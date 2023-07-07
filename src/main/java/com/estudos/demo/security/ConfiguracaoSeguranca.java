package com.estudos.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.estudos.demo.repository.UsuarioRepository;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSeguranca {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private LoginSucesso loginSucesso;

	@Bean
	public BCryptPasswordEncoder gerarCripitografia() {
		BCryptPasswordEncoder cripitografia = new BCryptPasswordEncoder();
		return cripitografia;
	}

	public UserDetailsService userDetailsServiceBean() {
		DetalheUsuarioServico detalheDoUsuario = new DetalheUsuarioServico(usuarioRepository);
		return detalheDoUsuario;
	}

	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/auth/admin/**", "/usuario/admin/**").hasAnyAuthority("ADMIN")
				.requestMatchers("/auth/biblio/**").hasAnyAuthority("BIBLIOTECARIO")
				.requestMatchers("/auth/user/**").hasAnyAuthority("USER", "ADMIN", "BIBLIOTECARIO")
				.requestMatchers("/**").permitAll()
				.anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedPage("/auth/auth-acesso-negado")
				.and().formLogin().loginPage("/login")
				.and().formLogin().successHandler(loginSucesso)
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and().logout().logoutSuccessUrl("/").permitAll()
				.and().httpBasic(Customizer.withDefaults());
		return http.build();
	}

	public void authenticationManagerBean(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService detalheDoUsuario = userDetailsServiceBean();
		BCryptPasswordEncoder criptografia = gerarCripitografia();

		auth.userDetailsService(detalheDoUsuario).passwordEncoder(criptografia);
	}
}

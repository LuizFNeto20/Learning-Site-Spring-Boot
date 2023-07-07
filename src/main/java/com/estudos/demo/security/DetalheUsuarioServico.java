package com.estudos.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.estudos.demo.modelo.Usuario;
import com.estudos.demo.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	public DetalheUsuarioServico(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByLogin(username);

		if(usuario != null && usuario.isAtivo()) {
			DetalheUsuario detalheUsuario = new DetalheUsuario(usuario);
			return detalheUsuario;
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

}

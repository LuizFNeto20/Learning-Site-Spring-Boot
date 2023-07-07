package com.estudos.demo.view;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Component
public class ConfiguracaoVisao implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/auth-acesso-negado").setViewName("/auth/auth-acesso-negado");
    }

}
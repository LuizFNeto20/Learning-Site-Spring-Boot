package com.estudos.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudos.demo.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);
}

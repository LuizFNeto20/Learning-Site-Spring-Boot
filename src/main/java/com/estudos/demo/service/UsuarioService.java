package com.estudos.demo.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.estudos.demo.modelo.Usuario;

public interface UsuarioService {

    public void apagarUsuarioPorId(Long id);

    public Usuario buscarUsuarioPorId(Long id);

    public Usuario buscarUsuarioPorLogin(String Login);

    public Usuario gravarUsuario(Usuario usuario);

    public void alterarUsuario(Usuario usuario);

    public List<Usuario> listarUsuarios(Sort sort);

    public void atribuirPapelUsuario(Long id, int[] idPapeis, boolean isAtivo);
}

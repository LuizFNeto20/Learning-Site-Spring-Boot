package com.estudos.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.estudos.demo.modelo.Papel;
import com.estudos.demo.modelo.Usuario;
import com.estudos.demo.repository.UsuarioRepository;

@Service
public class UsuarioServiceImplements implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PapelService papelService;

    @Autowired
    private BCryptPasswordEncoder criptografia;

    @Override
    public void apagarUsuarioPorId(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuarioRepository.delete(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new IllegalArgumentException("Usuario com id : " + id + " não encontrado");
        }
    }

    @Override
    public Usuario buscarUsuarioPorLogin(String Login) {
        Usuario usuario = usuarioRepository.findByLogin(Login);
        return usuario;
    }

    @Override
    public Usuario gravarUsuario(Usuario usuario) {
        Papel papel = papelService.buscarPapel("USER");
        List<Papel> papeis = new ArrayList<>();
        papeis.add(papel);
        usuario.setPapeis(papeis);

        String senhaCriptografada = criptografia.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    @Override
    public void alterarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios(Sort sort) {
        List<Usuario> usuarios = usuarioRepository.findAll(sort);
        return usuarios;
    }

    @Override
    public void atribuirPapelUsuario(Long id, int[] idPapeis, boolean isAtivo) {
        List<Papel> papeis = new ArrayList<>();
        for (int i = 0; i < idPapeis.length; i++) {
            long idPapel = idPapeis[i];
            Papel papel = papelService.buscarPapelporId(idPapel);
            papeis.add(papel);
        }
        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setPapeis(papeis);
        usuario.setAtivo(isAtivo);
        alterarUsuario(usuario);
    }
}

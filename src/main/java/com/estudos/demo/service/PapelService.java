package com.estudos.demo.service;

import java.util.List;

import com.estudos.demo.modelo.Papel;

public interface PapelService {

    public Papel buscarPapelporId(Long id);

    public Papel buscarPapel(String papel);

    public List<Papel> listarPapel();

}

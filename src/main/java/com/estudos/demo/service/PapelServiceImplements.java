package com.estudos.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudos.demo.modelo.Papel;
import com.estudos.demo.repository.PapelRepository;

@Service
public class PapelServiceImplements implements PapelService {

    @Autowired
    private PapelRepository papelRepository;

    @Override
    public Papel buscarPapelporId(Long id) {
        Optional<Papel> papel = papelRepository.findById(id);
        if (papel.isPresent()) {
            return papel.get();
        } else {
            throw new IllegalArgumentException("Papel com id: " + id + " não encontrado");
        }
    }

    @Override
    public Papel buscarPapel(String papel) {
        Papel pp = papelRepository.findByPapel(papel);
        return pp;
    }

    @Override
    public List<Papel> listarPapel() {
        List<Papel> papeis = papelRepository.findAll();
        return papeis;
    }

}

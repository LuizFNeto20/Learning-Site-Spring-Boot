package com.estudos.demo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.estudos.demo.modelo.Papel;
import com.estudos.demo.repository.PapelRepository;

@Component
public class CarregadoraDados implements CommandLineRunner {

    @Autowired
    private PapelRepository papelRepository;

    @Override
    public void run(String... args) throws Exception {
        String[] papeis = { "ADMIN", "USER", "BIBLIOTECARIO" };

        for (String papelString : papeis) {
            Papel papel = papelRepository.findByPapel(papelString);
            if (papel == null) {
                papel = new Papel(papelString);
                papelRepository.save(papel);
            }
        }
    }
}

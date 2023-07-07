package com.estudos.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estudos.demo.modelo.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

    Papel findByPapel(String papel);

}
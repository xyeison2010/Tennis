package com.leo.tennis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leo.tennis.model.Entrenador;


@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {

}

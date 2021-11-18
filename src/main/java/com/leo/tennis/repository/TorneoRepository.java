package com.leo.tennis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leo.tennis.model.Torneo;



public interface TorneoRepository extends JpaRepository<Torneo, Long> {

}

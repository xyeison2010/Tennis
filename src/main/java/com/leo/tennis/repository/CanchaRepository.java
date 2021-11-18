package com.leo.tennis.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leo.tennis.model.Cancha;

@Repository
public interface CanchaRepository extends JpaRepository<Cancha, Long> {

}

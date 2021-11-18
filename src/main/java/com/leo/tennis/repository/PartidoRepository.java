package com.leo.tennis.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leo.tennis.model.Partido;



//Aca va la anotacion Repository para indicar que es un repositorio y pueda ser detectado por el autowired
@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {

}

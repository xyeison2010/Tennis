package com.leo.tennis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leo.tennis.model.Jugador;

import java.util.List;

//Aca va la anotacion Repository para indicar que es un repositorio y pueda ser detectado por el autowired
@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

    List<Jugador> findAllByOrderByNombreAsc();
//este ..OrderByNombreAsc por defecto jpa busca en la entidad jugador si hay nombre
    
    
}
//los mas importantes propiedades de repository son :
// delete, findById, existsById, save, findAll, getOne
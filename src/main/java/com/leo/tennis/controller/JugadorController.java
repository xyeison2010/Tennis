package com.leo.tennis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.service.JugadorService;

import java.util.List;


@RestController //Se indica por medio de Springboot Annotation que es un RestController
@CrossOrigin //Esta anotacion permite llamadas desde cualquier origen, si no estuviera este deberia ser indicado en property
@RequestMapping("springtennis/api/v1/jugadores") //El path por el cual se accede a este controller
public class JugadorController {

	
    private  JugadorService jugadorService;


    
    @Autowired 
    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> listAll() {
        return ResponseEntity.ok(jugadorService.listAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.getById(id));
    }


    @PostMapping
    public ResponseEntity<JugadorDTO> saveJugador(@RequestBody JugadorDTO jugador) {
        JugadorDTO savedJugador = jugadorService.save(jugador);
        return new ResponseEntity<>(savedJugador, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JugadorDTO> updateJugador(@PathVariable Long id, @RequestBody JugadorDTO jugador) {
        jugador.setId(id);
        JugadorDTO updatedJugador = jugadorService.update(jugador);
        return ResponseEntity.ok(updatedJugador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJugador(@PathVariable Long id) {
        jugadorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/actions/recalculateRanking")
    public ResponseEntity<Void> recalculateRanking(@PathVariable Long id) {
        jugadorService.recalculateRanking(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
  
}
	



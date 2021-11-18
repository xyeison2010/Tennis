package com.leo.tennis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leo.tennis.dto.EntrenadorDTO;
import com.leo.tennis.service.EntrenadorService;



@RestController
@CrossOrigin
@RequestMapping("springtennis/api/v1/entrenadores")
public class EntrenadorController {


	
	private EntrenadorService entrenadorService;

	@Autowired
	public EntrenadorController(EntrenadorService entrenadorService) {
		this.entrenadorService = entrenadorService;
	}
	
	@GetMapping("")
	public ResponseEntity<List<EntrenadorDTO>> listAll() {
		return ResponseEntity.ok(entrenadorService.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntrenadorDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(entrenadorService.getById(id));
	}

/*
	@PostMapping(value = "")
	public ResponseEntity<EntrenadorDTO> saveEntrenador(@RequestBody EntrenadorDTO entrenador) {
		EntrenadorDTO savedEntrenador = entrenadorService.save(entrenador);
		return new ResponseEntity<>(savedEntrenador, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<EntrenadorDTO> updateEntrenador(@PathVariable Long id, @RequestBody EntrenadorDTO entrenador) {
		entrenador.setId(id);
		EntrenadorDTO updatedEntrenador = entrenadorService.update(entrenador);
		return ResponseEntity.ok(updatedEntrenador);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteEntrenador(@PathVariable Long id) {
		entrenadorService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	*/
}

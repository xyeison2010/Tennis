package com.leo.tennis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leo.tennis.dto.CanchaDTO;
import com.leo.tennis.service.CanchaService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("springtennis/api/v1/canchas")
public class CanchaController {

	private CanchaService canchaService;

	@Autowired
	public CanchaController(CanchaService canchaService) {
		this.canchaService = canchaService;
	}

	@GetMapping("")
	public ResponseEntity<List<CanchaDTO>> listAll() {
		return ResponseEntity.ok(canchaService.listAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CanchaDTO> getById(@PathVariable Long id) {
		return ResponseEntity.ok(canchaService.getById(id));
	}

	@PostMapping(value = "")
	public ResponseEntity<CanchaDTO> saveCancha(@RequestBody CanchaDTO cancha) {
		CanchaDTO savedCancha = canchaService.save(cancha);
		return new ResponseEntity<>(savedCancha, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<CanchaDTO> updateCancha(@PathVariable Long id, @RequestBody CanchaDTO cancha) {
		cancha.setId(id);
		CanchaDTO updatedCancha = canchaService.update(cancha);
		return ResponseEntity.ok(updatedCancha);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCancha(@PathVariable Long id) {
		canchaService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

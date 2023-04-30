package com.leo.tennis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.leo.tennis.dto.TorneoDTO;
import com.leo.tennis.service.TorneoService;



@Controller
@CrossOrigin
@RequestMapping("springtennis/api/v1/torneos") 
public class TorneoController {

	
    private final TorneoService torneoService;

    @Autowired
    public TorneoController(TorneoService torneoService) {
		this.torneoService = torneoService;
	}
	
	
    @GetMapping("")
    public ResponseEntity<List<TorneoDTO>> listAll() {
        return ResponseEntity.ok(torneoService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TorneoDTO> getById(@PathVariable Long id) {
    	TorneoDTO torneo = torneoService.getById(id);
        return ResponseEntity.ok(torneo);
    }

    @PostMapping("")
    public ResponseEntity<TorneoDTO> savePartido(@RequestBody TorneoDTO torneoDTO) {//esto es para crear guardar
    	TorneoDTO savedTorneo = torneoService.save(torneoDTO);
        return new ResponseEntity<>(
        		savedTorneo,
                HttpStatus.CREATED);
    }


}

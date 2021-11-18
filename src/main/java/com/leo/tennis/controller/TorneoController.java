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

	@Autowired
    private TorneoService torneoService;

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

/*
    @PutMapping(value = "/{id}")
    public ResponseEntity<PartidoDTO> updatePartido(@PathVariable Long id, @RequestBody PartidoDTO partido) {
        partido.setId(id);
        PartidoDTO updatedPartido = partidoService.update(partido);
        return ResponseEntity.ok(updatedPartido);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePartido(@PathVariable Long id) {
        ResponseEntity<Void> response;
        partidoService.delete(id);
        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "/{id}/actions/sumar-punto")
    public ResponseEntity<PartidoDTO> sumarPuntos(@PathVariable Long id, @RequestParam ModoJugador modoJugador) {
        PartidoDTO partido = partidoService.sumarPuntos(id, modoJugador);
        return new ResponseEntity<>(partido, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/actions/init")
    public ResponseEntity<Void> initGame(@PathVariable Long id) {
        partidoService.initGame(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	*/
}

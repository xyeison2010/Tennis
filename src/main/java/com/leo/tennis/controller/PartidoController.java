package com.leo.tennis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leo.tennis.dto.PartidoDTO;
import com.leo.tennis.enums.ModoJugador;
import com.leo.tennis.service.PartidoService;

import java.util.List;


/**
 * <p>Controller de Partido</p>
 * Aca se exponen los metodos que seran accesibles via HttpRequest por medio de los path declarados,
 * estos seran accesibles por dichas llamadas por medio de fetch (front), RestTemplate(Otras apis)
 * o postman
 */
@RestController 
@CrossOrigin 
@RequestMapping("springtennis/api/v1/partidos") 
public class PartidoController {

	
    private final PartidoService partidoService;

    @Autowired
    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }
   construye al momento del retorno del service
     * por medio del ResponseEntity.ok() con un HttpStatus.Ok (200)*/
    @GetMapping
    public ResponseEntity<List<PartidoDTO>> listAll() {
        return ResponseEntity.ok(partidoService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getById(@PathVariable Long id) {
        PartidoDTO partido = partidoService.getById(id);
        return ResponseEntity.ok(partido);
    }

  
    @PostMapping
    public ResponseEntity<PartidoDTO> savePartido(@RequestBody PartidoDTO partido) {//esto es para crear guardar
        PartidoDTO savedPartido = partidoService.save(partido);
        return new ResponseEntity<>(
                savedPartido,
                HttpStatus.CREATED);
    }

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

}

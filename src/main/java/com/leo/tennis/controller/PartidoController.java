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

	@Autowired
    private final PartidoService partidoService;

    /* esto sirve solo para testear */
    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }
    /*     */
    
    /*
     * Aca se esta exponiendo por medio del controller un metodo del service, en este caso particular llamado
     * listAll que devuelve todos los partidos, se le indica por medio del annotation de springboot GetMapping
     * que se va a llamar por medio de un request del tipo Get al path definido en el componente RequestMapping,
     * en este caso no tiene parametros de entrada ni por path (link) ni por body, de retorno indica que deberia dar
     * un ResponseEntity que contiene una lista de partidos, en cual se construye al momento del retorno del service
     * por medio del ResponseEntity.ok() con un HttpStatus.Ok (200)*/
    @GetMapping
    public ResponseEntity<List<PartidoDTO>> listAll() {
        return ResponseEntity.ok(partidoService.listAll());
    }

    /*
     * En este caso se devuelve un partido particular, como se vera en el GetMapping se le indica
     * que se le agrega al path el /id y por medio de @PathVariable se indica que se obtiene dicho parametro
     * por el path de la llamada request, de la misma forma se llama al service y se devuelve el ResponseEntity
     * con el partido adentro (body) del response*/
    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> getById(@PathVariable Long id) {
        PartidoDTO partido = partidoService.getById(id);
        return ResponseEntity.ok(partido);
    }

    /*
     * En este caso se expone un metodo Post request, y por medio de la anotacion de @RequestBody
     * se solicita como parametro un objeto de tipo PartidoDTO que le va a entrar como JSON por la llamada
     * (Spring lo transforma automaticamente) y lo utiliza como parametro de entrada en el service*/
    //const agregarPartido => asi esta en el front
    @PostMapping
    public ResponseEntity<PartidoDTO> savePartido(@RequestBody PartidoDTO partido) {//esto es para crear guardar
        PartidoDTO savedPartido = partidoService.save(partido);
        return new ResponseEntity<>(
                savedPartido,
                HttpStatus.CREATED);
    }

    /*
     * Aca se ven ambos casos, se solicita un ID que se obtiene por PathVariable como parametro de entrada,
     * y un DTO de PartidoDTO que se obtiene por medio de RequestBody como parametro, se utiliza para identificar
     * el id del partido que se quiere modificar y reemplazar sus valores con el body del request, que entra como Json
     * Object y el sistema lo parsea automaticamente a objeto al momento de la llamada al controller, tambien se declara
     * que esto es un metodo PUT*/
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

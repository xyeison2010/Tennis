package com.leo.tennis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.service.JugadorService;

import java.util.List;

/**
 * <p>Controller de Jugador</p>
 * Aca se exponen los metodos que seran accesibles via HttpRequest por medio de los path declarados,
 * estos seran accesibles por dichas llamadas por medio de fetch (front), RestTemplate(Otras apis)
 * o postman
 */
@RestController //Se indica por medio de Springboot Annotation que es un RestController
@CrossOrigin //Esta anotacion permite llamadas desde cualquier origen, si no estuviera este deberia ser indicado en property
@RequestMapping("springtennis/api/v1/jugadores") //El path por el cual se accede a este controller
public class JugadorController {

	 @Autowired
    private  JugadorService jugadorService;


    /* esto sirve para testear nomas*/
	 
    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }
    /*     */
    
    
    /*
     * Aca se esta exponiendo por medio del controller un metodo del service, en este caso particular llamado
     * listAll que devuelve todos los jugadores, se le indica por medio del annotation de springboot GetMapping
     * que se va a llamar por medio de un request del tipo Get al path definido en el componente RequestMapping,
     * en este caso no tiene parametros de entrada ni por path (link) ni por body, de retorno indica que deberia dar
     * un ResponseEntity que contiene una lista de jugadores, en cual se construye al momento del retorno del service
     * por medio del ResponseEntity.ok() con un HttpStatus.Ok (200)*/
    @GetMapping
    public ResponseEntity<List<JugadorDTO>> listAll() {
        return ResponseEntity.ok(jugadorService.listAll());
    }

    /*
     * En este caso se devuelve un jugador particular, como se vera en el GetMapping se le indica
     * que se le agrega al path el /id y por medio de @PathVariable se indica que se obtiene dicho parametro
     * por el path de la llamada request, de la misma forma se llama al service y se devuelve el ResponseEntity
     * con el jugador adentro (body) del response*/
    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.getById(id));
    }

    /*
     * En este caso se expone un metodo Post request, y por medio de la anotacion de @RequestBody
     * se solicita como parametro un objeto de tipo JugadorDto que le va a entrar como JSON por la llamada
     * (Spring lo transforma automaticamente) y lo utiliza como parametro de entrada en el service*/
    @PostMapping
    public ResponseEntity<JugadorDTO> saveJugador(@RequestBody JugadorDTO jugador) {
        JugadorDTO savedJugador = jugadorService.save(jugador);
        return new ResponseEntity<>(savedJugador, HttpStatus.CREATED);
    }

    /*
     * Aca se ven ambos casos, se solicita un ID que se obtiene por PathVariable como parametro de entrada,
     * y un DTO de JugadorDto que se obtiene por medio de RequestBody como parametro, se utiliza para identificar
     * el id del jugador que se quiere modificar y reemplazar sus valores con el body del request, que entra como Json
     * Object y el sistema lo parsea automaticamente a objeto al momento de la llamada al controller, tambien se declara
     * que esto es un metodo PUT*/
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
  //response entity  nos ayuda manejar una respuesta adecuada http
  //request body es el objeto q estamos pasando dentro de nuestra request asincrono
}
	



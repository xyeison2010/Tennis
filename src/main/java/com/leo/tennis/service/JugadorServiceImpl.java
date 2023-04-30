package com.leo.tennis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.enums.Estado;
import com.leo.tennis.mapper.JugadorMapper;
import com.leo.tennis.model.Jugador;
import com.leo.tennis.model.Partido;
import com.leo.tennis.repository.JugadorRepository;
import com.leo.tennis.repository.PartidoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

//Aca va la anotacion @Service para declarar que es un servicio y pueda ser detectado por el autowired
@Service
public class JugadorServiceImpl implements JugadorService {

    //String que se utilizan para las respuestas de los exception handler
    public static final String PLAYER_WITH_ID = "Player with id = ";
    public static final String DOES_NOT_EXIST = " does not exist.";
    public static final String ALREADY_EXISTS = " already exists.";

    
   private JugadorRepository jugadorRepository;
   
    private  PartidoRepository partidoRepository;

    private JugadorMapper jugadorMapper;


    @Autowired
    public JugadorServiceImpl(JugadorRepository jugadorRepository,
                              PartidoRepository partidoRepository,
                              JugadorMapper jugadorMapper) {
        this.jugadorRepository = jugadorRepository;
        this.partidoRepository = partidoRepository;
        this.jugadorMapper = jugadorMapper;
    }

    @Override
    public List<JugadorDTO> listAll() {
        /*
         * Se obtiene una collection de jugadores (entidad) del repository,
         * se transforma a una collection de jugadores (DTO) y luego
         * se lo transforma en un ArrayList para devolverlo al controller*/
        return jugadorRepository.findAllByOrderByNombreAsc().stream()
                .map(this.jugadorMapper::toDTO)
                .collect(Collectors.toList());//iteracion de map a todto
    }

    @Override
    public JugadorDTO getById(Long id) {
        /*Llama al repository en el metodo findById, para obtener una entidad jugador, la cual la transforma
         * a DTO con el .map() en caso de que el .map arroje conjunto vacio arroja una excepcion de tipo NoSuchElementException*/
        return jugadorRepository.findById(id).map(this.jugadorMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException(PLAYER_WITH_ID + id + DOES_NOT_EXIST));
    }


    @Override
    public JugadorDTO save(JugadorDTO jugador) {

        boolean exists = jugador.getId() != null && jugadorRepository.existsById(jugador.getId());
        if (exists) { //Si existe arroja una nueva excepcion del tipo IllegalArgumentException
            throw new IllegalArgumentException(PLAYER_WITH_ID + jugador.getId() + ALREADY_EXISTS);
        }
        //En caso de que la verificacion anterior no suceda, continua el flujo y guarda la entidad con el save
        //Tambien devuelve el jugador guardado ya que el repository lo devuelve....
        return this.jugadorMapper.toDTO(jugadorRepository.save(this.jugadorMapper.fromDTO(jugador)));
    }

//lo mismo q save
    @Override
    public JugadorDTO update(JugadorDTO jugador) {
        boolean exists = jugadorRepository.existsById(jugador.getId());
        if (!exists) {
            throw new NoSuchElementException(PLAYER_WITH_ID + jugador.getId() + DOES_NOT_EXIST);
        }
        return this.jugadorMapper.toDTO(jugadorRepository.save(this.jugadorMapper.fromDTO(jugador)));
    }

    @Override
    public void delete(Long id) {
    
        boolean exists = jugadorRepository.existsById(id);
        if (!exists) {
            throw new NoSuchElementException(PLAYER_WITH_ID + id + DOES_NOT_EXIST);
        }
        jugadorRepository.deleteById(id);
    }

    @Override
    public JugadorDTO recalculateRanking(Long id) {
        Optional<Jugador> optJugador = jugadorRepository.findById(id); //optional q contiene valor null o no        
        if (optJugador.isPresent()) {
            Jugador jugador = optJugador.get();//si el jugador q esta presente lo agarramos con optional

            List<Partido> partidos = partidoRepository.findAll();

            // Define las constantes con los indices definidos para el calculo
            final int INDICADOR_GANADOS_LOCAL = 10;
            final int INDICADOR_GANADOS_VISITANTE = 15;
            final int INDICADOR_PERDIDOS_LOCAL = -5;

            // Inicializa el ranking inicial
            int ranking = 0;

            List<Partido> partidosGanadosLocal;
            List<Partido> partidosGanadosVisitante;
            List<Partido> partidosPerdidosLocal;

            partidosGanadosLocal = listPartidosGanadosLocalDeJugador(id, partidos);
            partidosGanadosVisitante = listPartidosGanadosVisitanteDeJugador(id, partidos);
            partidosPerdidosLocal = listPartidosPerdidosLocalDeJugador(id, partidos);

            // Realiza el calculo por cada indicador
            ranking += partidosGanadosLocal.size() * INDICADOR_GANADOS_LOCAL;
            ranking += partidosGanadosVisitante.size() * INDICADOR_GANADOS_VISITANTE;
            ranking += partidosPerdidosLocal.size() * INDICADOR_PERDIDOS_LOCAL;

            // Si el ranking obtenido es menor a 0 se setea en 0
            ranking = Math.max(ranking, 0);

            // Se resetean los puntos en el jugador
            jugador.setPuntos(ranking);
            return this.jugadorMapper.toDTO(jugadorRepository.save(jugador));
        } else{
            throw new NoSuchElementException(PLAYER_WITH_ID + id + DOES_NOT_EXIST);
        }
    }

    private List<Partido> listPartidosGanadosLocalDeJugador(Long id, List<Partido> partidos) {
        List<Partido> partidosFiltrados = new ArrayList<>();
        for(Partido p : partidos){
            if(p.getEstado() == Estado.FINALIZADO && p.getJugadorLocal().getId().equals(id) && p.getCantidadGamesLocal() == 6)
                partidosFiltrados.add(p);
           // p.getJugadorLocal().setGanancia(0);
        }
        return partidosFiltrados;
    }

    private List<Partido> listPartidosGanadosVisitanteDeJugador(Long id, List<Partido> partidos) {
        List<Partido> partidosFiltrados = new ArrayList<>();
        for(Partido p : partidos){
            if(p.getEstado() == Estado.FINALIZADO && p.getJugadorVisitante().getId().equals(id) && p.getCantidadGamesVisitante() == 6)
                partidosFiltrados.add(p);
        }
        return partidosFiltrados;
    }

    private List<Partido> listPartidosPerdidosLocalDeJugador(Long id, List<Partido> partidos) {
        List<Partido> partidosFiltrados = new ArrayList<>();
        for(Partido p : partidos){
            if(p.getEstado() == Estado.FINALIZADO && p.getJugadorLocal().getId().equals(id) && p.getCantidadGamesVisitante() == 6)
                partidosFiltrados.add(p);
        }
        return partidosFiltrados;
    }


}
	





package com.leo.tennis.service;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.dto.TorneoDTO;
import com.leo.tennis.mapper.TorneoMapper;
import com.leo.tennis.model.Torneo;
import com.leo.tennis.repository.TorneoRepository;


@Service
public class TorneoServiceImpl implements TorneoService{
    //String que se utilizan para las respuestas de los exception handler
    public static final String DOES_NOT_EXIST = " does not exist.";
    public static final String PARTIDO_WITH_ID = "Partido with id = ";
    public static final String ALREADY_EXISTS = " already exists.";
    public static final String NOT_IN_PROGRESS = " is not in progress. ";
    private static final String ALREADY_IN_PROGRESS = " is already in progress or is finished. ";
    public static final String SCORE_IMPOSIBLE = "Score imposible";
    private static final String PLAYER_MISSING = "Se deben agregar ambos jugadores.";
    private static final String PLAYER_DUPLICATED = "Los jugadores agregados deben ser distintos.";
    private static final String INVALID_DATE = "La fecha/hora de inicio debe ser mayor o igual a la fecha/hora actual.";
    private static final String INVALID_DATE_2 = "La fecha/hora de inicio no puede estar en el mismo rango de 4 horas con la misma cancha";
	TorneoRepository torneoRepository;
	
	TorneoMapper torneoMapper;

	JugadorRepository jugadorRepository;
	
	
	
	@Autowired
	 public TorneoServiceImpl(TorneoRepository torneoRepository, TorneoMapper torneoMapper,
			JugadorRepository jugadorRepository) {
		
		this.torneoRepository = torneoRepository;
		this.torneoMapper = torneoMapper;
		this.jugadorRepository = jugadorRepository;
	}
	
	
	@Override
	public List<TorneoDTO> listAll() {
		   return torneoRepository.findAll()
	                .stream()
	                .map(this.torneoMapper::toDTO)
	                .collect(Collectors.toList());
	}

	@Override
	public TorneoDTO getById(Long id) {
		 return torneoRepository.findById(id).map(this.torneoMapper::toDTO)
	                .orElseThrow(() -> new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST));
	}

	   private void validarJugadores(TorneoDTO torneoDto) {
	        JugadorDTO jugador1 = torneoDto.getJugador1();
	        JugadorDTO jugador2 = torneoDto.getJugador2();
	        JugadorDTO jugador3 = torneoDto.getJugador3();
	        JugadorDTO jugador4 = torneoDto.getJugador4();

	        if (jugador1 != null && jugador2 != null && jugador3 !=null && jugador4 !=null ) {
	            if (jugador1.getId().equals(jugador2.getId()) || jugador3.getId().equals(jugador4.getId()) || jugador1.getId().equals(jugador4.getId()) || jugador2.getId().equals(jugador3.getId())  ) {
	                throw new IllegalArgumentException(PLAYER_DUPLICATED);
	            }
	        } else {
	            throw new IllegalArgumentException(PLAYER_MISSING);
	        }
	    }

	    private void validarFechaYHora(TorneoDTO torneoDto) {
	        Date now = new Date();
	        if (torneoDto.getFechaComienzo().compareTo(now) < 0) {//si la fecha q escogiste es antes del dia actual ,arroja invalidacion
	            throw new IllegalArgumentException(INVALID_DATE);
	        }
	    }
	    
	    private Calendar convertirDateACalendar(Date fecha){
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(fecha);
	        return calendar;
	    }
	
	    private void validar4Horas(TorneoDTO torneoParametro){
	        List<Torneo> listaTorneos = torneoRepository.findAll();
	        Calendar fechaTorneoParametro = convertirDateACalendar(torneoParametro.getFechaComienzo());
	        Long idCanchaParametro = torneoParametro.getCancha().getId();

	        for (Torneo torneo : listaTorneos){
	            Long idCancha = torneo.getCancha().getId();
	            Calendar fechaTorneo = convertirDateACalendar(torneo.getFechaComienzo());
	            long horas = Math.abs(ChronoUnit.HOURS.between(fechaTorneo.toInstant(), fechaTorneoParametro.toInstant()));
	            if(idCancha.equals(idCanchaParametro)){
	                if(horas < 4){
	                    throw new IllegalArgumentException(INVALID_DATE_2);
	                }
	            }
	        }
	    }

	    private void validarNuevoPartido(TorneoDTO torneoDto) {
	        /*
	        * En este metodo se valida el partido entrante por parametro,
	        * primero se fija que el partido no exista, alojando la respuesta en una variable booleana,
	        * se fija que en el partido entrante por parametro haya una Id distinta de null y verifica esa id
	        * en el repository por medio del metodo ExistById, si existe arroja una excepcion del tipo IllegalArgumentException
	        * luego llamada a los metodos validarJugadores y validarFechaYHora */
	        boolean exists = torneoDto.getId() != null && torneoRepository.existsById(torneoDto.getId());
	        if (exists) {
	            throw new IllegalArgumentException(PARTIDO_WITH_ID + torneoDto.getId() + ALREADY_EXISTS);
	        }
	        this.validarJugadores(torneoDto);
	        this.validarFechaYHora(torneoDto);
	        this.validar4Horas(torneoDto);
	    }

		@Override
		public TorneoDTO save(TorneoDTO torneoDto) {
			/*  En este metodo se guarda el partido recibido por parametro, primero se llama a la funcion
		        * validarNuevoPartido, luego se convierte el partidoDTO a entidad partido y se la envia al repository
		        * al metodo save*/
		        this.validarNuevoPartido(torneoDto);
		        return this.torneoMapper.toDTO(torneoRepository.save(this.torneoMapper.fromDTO(torneoDto)));
		}
}

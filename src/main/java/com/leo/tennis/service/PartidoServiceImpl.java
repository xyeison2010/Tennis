package com.leo.tennis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.dto.PartidoDTO;
import com.leo.tennis.enums.Estado;
import com.leo.tennis.enums.ModoJugador;
import com.leo.tennis.mapper.PartidoMapper;
import com.leo.tennis.model.Partido;
import com.leo.tennis.repository.PartidoRepository;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
//el tennis tiene 6 rondas
@Service
public class PartidoServiceImpl implements PartidoService {
    //Int que se utilizan para validaciones
    public static final int SCORE_ADV = 4;
    public static final int SCORE_40 = 3;
    public static final int SCORE_30 = 2;
    public static final int SCORE_15 = 1;
    public static final int SCORE_0 = 0;

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

 
   private  PartidoRepository partidoRepository;
    
    private  PartidoMapper partidoMapper;
       
   
    @Autowired
    public PartidoServiceImpl(PartidoRepository partidoRepository, PartidoMapper partidoMapper) {
        this.partidoRepository = partidoRepository;
        this.partidoMapper = partidoMapper;
    }
  
    private static final Map<Integer, String> descriptions = new HashMap<>();

    static {
        descriptions.put(SCORE_ADV, "Adv");
        descriptions.put(SCORE_40, "40");
        descriptions.put(SCORE_30, "30");
        descriptions.put(SCORE_15, "15");
        descriptions.put(SCORE_0, "0");
    }



    @Override
    public List<PartidoDTO> listAll() {
        /*
         * Se obtiene una collection de partidos (entidad) del repository,
         * se transforma a una collection de partidos (DTO) y luego
         * se lo transforma en un ArrayList para devolverlo al controller*/
        return partidoRepository.findAll()
                .stream()
                .map(this.partidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PartidoDTO getById(Long id) {
        /*Llama al repository en el metodo findById, para obtener una entidad partido, la cual la transforma
         * a DTO con el .map() en caso de que el .map arroje conjunto vacio arroja una excepcion de tipo NoSuchElementException*/
        return partidoRepository.findById(id).map(this.partidoMapper::toDTO)
                .orElseThrow(() -> new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST));
    }

    private void validarJugadores(PartidoDTO partidoDto) {
 
        JugadorDTO jugadorLocal = partidoDto.getJugadorLocal();
        JugadorDTO jugadorVisitante = partidoDto.getJugadorVisitante();

        if (jugadorLocal != null && jugadorVisitante != null) {
            if (jugadorLocal.getId().equals(jugadorVisitante.getId())) {
                throw new IllegalArgumentException(PLAYER_DUPLICATED);
            }
        } else {
            throw new IllegalArgumentException(PLAYER_MISSING);
        }
    }

    private void validarFechaYHora(PartidoDTO partido) {
  
        Date now = new Date();
        if (partido.getFechaComienzo().compareTo(now) < 0) {//si la fecha q escogiste es antes del dia actual ,arroja invalidacion
            throw new IllegalArgumentException(INVALID_DATE);
        }
    }

    private Calendar convertirDateACalendar(Date fecha){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar;
    }
    private void validar4Horas(PartidoDTO partidoParametro){
        List<Partido> listaPartidos = partidoRepository.findAll();
        Calendar fechaPartidoParametro = convertirDateACalendar(partidoParametro.getFechaComienzo());
        Long idCanchaParametro = partidoParametro.getCancha().getId();

        for (Partido partido : listaPartidos){
            Long idCancha = partido.getCancha().getId();
            Calendar fechaPartido = convertirDateACalendar(partido.getFechaComienzo());
            long horas = Math.abs(ChronoUnit.HOURS.between(fechaPartido.toInstant(), fechaPartidoParametro.toInstant()));
            if(idCancha.equals(idCanchaParametro)){
                if(horas < 4){
                    throw new IllegalArgumentException(INVALID_DATE_2);
                }
            }
        }
    }

    private void validarNuevoPartido(PartidoDTO partido) {
      
        boolean exists = partido.getId() != null && partidoRepository.existsById(partido.getId());
        if (exists) {
            throw new IllegalArgumentException(PARTIDO_WITH_ID + partido.getId() + ALREADY_EXISTS);
        }
        this.validarJugadores(partido);
        this.validarFechaYHora(partido);
        this.validar4Horas(partido);
    }

    @Override
    public PartidoDTO save(PartidoDTO partido) {
       
        this.validarNuevoPartido(partido);
        return this.partidoMapper.toDTO(partidoRepository.save(this.partidoMapper.fromDTO(partido)));
    }

    private void validarPartidoNoIniciado(Long id) {
   
        PartidoDTO partido = this.getById(id);
        if (!Estado.NO_INICIADO.equals(partido.getEstado())) {//estado del partido sea distinto a NO_INICIADO
            throw new IllegalArgumentException(PARTIDO_WITH_ID + partido.getId() + ALREADY_IN_PROGRESS);
        }
    }

    private void validarPartidoEditado(PartidoDTO partido) {
      
        boolean exists = partidoRepository.existsById(partido.getId());
        if (!exists) {
            throw new NoSuchElementException(PARTIDO_WITH_ID + partido.getId() + DOES_NOT_EXIST);
        }
        this.validarPartidoNoIniciado(partido.getId());
        this.validarJugadores(partido);
        this.validarFechaYHora(partido);
        this.validar4Horas(partido);
    }

    @Override
    public PartidoDTO update(PartidoDTO partido) {
       
        this.validarPartidoEditado(partido);
        return this.partidoMapper.toDTO(partidoRepository.save(this.partidoMapper.fromDTO(partido)));
    }

    private void validarPartidoEliminado(Long id) {
       
        boolean exists = partidoRepository.existsById(id);
        if (!exists) {
            throw new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST);
        }
        this.validarPartidoNoIniciado(id);
    }

    @Override
    public void delete(Long id) {
        /*En este metodo se trata de eliminar un partido por el id recibido por parametro,
        * primero se llama a validarPartidoEliminado y luego, se llama al repository metodo
        * deleteById para que lo elimine*/
        this.validarPartidoEliminado(id);
        partidoRepository.deleteById(id);
    }

    private String translateScore(int puntos) {
        /*Este metodo traduce el puntaje recibido por int a un key del hashmap descriptions*/
        return descriptions.get(puntos);
    }
// agrege ganancia
    private void gameLocal(Partido partido) {
        partido.setScoreLocal(0); //Se setean los puntos del score local a 0
        partido.setScoreVisitante(0); //se setean los puntos del score visitante a 0
        partido.setPuntosGameActualLocal(this.translateScore(partido.getScoreLocal())); //Se le cargan puntos al game local actual traducidos
        partido.setPuntosGameActualVisitante(this.translateScore(partido.getScoreVisitante()));//Se le cargan puntos al game visitante actual traducidos

        partido.setCantidadGamesLocal(partido.getCantidadGamesLocal() + 1); //Se le aumenta un game al local (ya que anoto el maximo puntaje)
        if (partido.getCantidadGamesLocal() == 6) { //Si llega a 6 rondas ganadas se finaliza el partido
            partido.setEstado(Estado.FINALIZADO);            
        }
        int ganancia = 0; final int INDICADOR_GANANCIA_SUPERIOR = 300; final int INDICADOR_GANANCIA_MENOR = 200; final int INDICADOR_GANANCIA_PERDIDA = 0;

        // si gana set con diferencia de 3 o mas 
        if (partido.getEstado() == Estado.FINALIZADO && (partido.getCantidadGamesLocal() - partido.getCantidadGamesVisitante() >= 3)) {
			if (partido.getCantidadGamesLocal() > partido.getCantidadGamesVisitante() && partido.getCantidadGamesLocal() >= 6) { 				
				ganancia = partido.getJugadorLocal().getGanancia()+ INDICADOR_GANANCIA_SUPERIOR;
			
				partido.getJugadorLocal().setGanancia(ganancia);
		} 
		}
        // si gana set con diferencia menor a 3 
        if (partido.getEstado() == Estado.FINALIZADO && (partido.getCantidadGamesLocal() - partido.getCantidadGamesVisitante() < 3)) {
			if (partido.getCantidadGamesLocal() > partido.getCantidadGamesVisitante() && partido.getCantidadGamesLocal() >= 6) { 
				ganancia = partido.getJugadorLocal().getGanancia()+INDICADOR_GANANCIA_MENOR;
				partido.getJugadorLocal().setGanancia(ganancia);					
			} 
		}
       // si pierde set
    	if (partido.getEstado() == Estado.FINALIZADO) {
			if ((partido.getCantidadGamesLocal() < partido.getCantidadGamesVisitante() && partido.getCantidadGamesVisitante() >= 6)) {
				ganancia =partido.getJugadorLocal().getGanancia()+ INDICADOR_GANANCIA_PERDIDA;
				partido.getJugadorLocal().setGanancia(ganancia);
				
			
			} }
    	
             
    }

    private void gameVisitante(Partido partido) {
        partido.setScoreLocal(0); //Se setean los puntos del score local a 0
        partido.setScoreVisitante(0); //se setean los puntos del score visitante a 0
        partido.setPuntosGameActualLocal(this.translateScore(partido.getScoreLocal())); //Se le cargan puntos al game local actual traducidos
        partido.setPuntosGameActualVisitante(this.translateScore(partido.getScoreVisitante())); //Se le cargan puntos al game visitante actual traducidos

        partido.setCantidadGamesVisitante(partido.getCantidadGamesVisitante() + 1); //Se le aumenta un game al visitante (ya que anoto el maximo puntaje)
        if (partido.getCantidadGamesVisitante() == 6) { //Si llega a 6 rondas ganadas se finaliza el partido
            partido.setEstado(Estado.FINALIZADO);
        }
        
        int ganancia = 0; final int INDICADOR_GANANCIA_SUPERIOR = 300; final int INDICADOR_GANANCIA_MENOR = 200; final int INDICADOR_GANANCIA_PERDIDA = 0;
		
	     if (partido.getEstado() == Estado.FINALIZADO && (partido.getCantidadGamesLocal() - partido.getCantidadGamesVisitante() >= 3)) {
				if (partido.getCantidadGamesVisitante() > partido.getCantidadGamesLocal() && partido.getCantidadGamesVisitante()  >= 6) { 				
					ganancia = partido.getJugadorVisitante().getGanancia()+ INDICADOR_GANANCIA_SUPERIOR;
					partido.getJugadorVisitante().setGanancia(ganancia);
				} 
			}
	        //
	        if (partido.getEstado() == Estado.FINALIZADO && (partido.getCantidadGamesLocal() - partido.getCantidadGamesVisitante() < 3)) {
				if (partido.getCantidadGamesVisitante() > partido.getCantidadGamesLocal()&& partido.getCantidadGamesVisitante() >= 6) { 
					ganancia = partido.getJugadorVisitante().getGanancia()+ INDICADOR_GANANCIA_MENOR;
					partido.getJugadorVisitante().setGanancia(ganancia);		
				} 
			}
	       //
	    	if (partido.getEstado() == Estado.FINALIZADO) {
				if ((partido.getCantidadGamesVisitante() < partido.getCantidadGamesLocal() && partido.getCantidadGamesLocal() >= 6)) {
					ganancia = partido.getJugadorVisitante().getGanancia()+ INDICADOR_GANANCIA_PERDIDA;
					partido.getJugadorVisitante().setGanancia(ganancia);
				
				} }	
        
    }

    @Override
    public void initGame(Long id) {
        /*En este metodo se inicia un partido
        * se obtiene un opcional de partido llamando a findById con el id obtenido por parametro*/
        Optional<Partido> optPartido = partidoRepository.findById(id);
        if (optPartido.isPresent()) { //Se fija que en el opcional de partido haya un partido
            validarPartidoNoIniciado(id); //Se valida que el partido no este iniciado
            Partido partido = optPartido.get(); //Se obtiene el partido dentro de opcional y se lo guarda en una variable
            partido.setEstado(Estado.EN_CURSO); //Se le asigna el estado EN CURSO al partido
            partidoRepository.save(partido); //Se guarda el partido
        } else {
            throw new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST); //Si no habia partido presente se arroja excepcion
        }
    }

    @Override
    public PartidoDTO sumarPuntos(Long id, ModoJugador modo) {
        //En esta funcion se le suman puntos al jugador en el id de partido
        Optional<Partido> optPartido = partidoRepository.findById(id); //Se busca un partido en el repository
        if (optPartido.isPresent()) { //Verifica que el opcional el partido este presente
            Partido partido = optPartido.get(); //Se obtiene el partido del opcional y lo guarda en una variable temporal de tipo Partido

            if (!Estado.EN_CURSO.equals(partido.getEstado())) {  //Verifica que el partido este en curso
                //Si el partido no esta en curso arroja una excepcion
                throw new IllegalArgumentException(PARTIDO_WITH_ID + partido.getId() + NOT_IN_PROGRESS);
            }

            if (modo == ModoJugador.LOCAL) { //Si el que sumo puntos es el local
                if (partido.getScoreVisitante() == SCORE_ADV) { //Si el visitante tiene el score maximo
                    if (partido.getScoreLocal() != SCORE_40) { //Si el score es distinto de 40 arroja excepcion
                        throw new IllegalArgumentException(SCORE_IMPOSIBLE);
                    }
                    partido.setScoreVisitante(partido.getScoreVisitante() - 1); //Setea el score del visitante al actual - 1
                } else { //Mismas logicas de arriba pero para el local
                    if (partido.getScoreLocal() == SCORE_ADV && partido.getScoreVisitante() != SCORE_40) {
                        throw new IllegalArgumentException(SCORE_IMPOSIBLE);
                    }
                    partido.setScoreLocal(partido.getScoreLocal() + 1);
                }
            } else { //Mismas logicas de arriba pero para la suma de puntos del visitante
                if (partido.getScoreLocal() == SCORE_ADV) {
                    if (partido.getScoreVisitante() != SCORE_40) {
                        throw new IllegalArgumentException(SCORE_IMPOSIBLE);
                    }
                    partido.setScoreLocal(partido.getScoreLocal() - 1);
                } else {
                    if (partido.getScoreVisitante() == SCORE_ADV && partido.getScoreLocal() != SCORE_40) {
                        throw new IllegalArgumentException(SCORE_IMPOSIBLE);
                    }
                    partido.setScoreVisitante(partido.getScoreVisitante() + 1);
                }
            }
            this.actualizarScore(partido); //se llama al metodo actualizar score
            return this.partidoMapper.toDTO(partidoRepository.save(partido)); //se guarda la entidad en el repository y se transforma el return a dto para response
        } else {
            throw new NoSuchElementException(PARTIDO_WITH_ID + id + DOES_NOT_EXIST);
        }
    }

    private void actualizarScore(Partido partido) {
        partido.setPuntosGameActualLocal(this.translateScore(partido.getScoreLocal())); //Se traduce el puntaje del score local a puntos de game
        partido.setPuntosGameActualVisitante(this.translateScore(partido.getScoreVisitante())); //lo mismo de arriba para visitante

        //Math abs devuelve el valor absoluto, en el if verifica que sea mayor o igual a 2
        //osea como son 6 rondas en el tennis y si la difierencia de rondas ganadas es 2 o mayor , automaticamente ganas 
        if (Math.abs(partido.getScoreLocal() - partido.getScoreVisitante()) >= 2) {
            if (partido.getScoreLocal() > partido.getScoreVisitante() && partido.getScoreLocal() >= SCORE_ADV) { //Si el score del local es mayor al visitante y tiene SCORE_ADV
                this.gameLocal(partido); //se llama al a funcion gameLocal
            } else if (partido.getScoreVisitante() > partido.getScoreLocal() && partido.getScoreVisitante() >= SCORE_ADV) { //Si el score del visitante es mayor al local y tiene SCORE_ADV
                this.gameVisitante(partido); //se llama a la funcion gameVisitante
            }
        }

    }
//score_Adv = 4
}

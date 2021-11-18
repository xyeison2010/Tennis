package com.leo.tennis.service;

import java.util.List;

import com.leo.tennis.dto.PartidoDTO;
import com.leo.tennis.enums.ModoJugador;



/**
 * <p>Service de Partido</p>
 * Este componente sera el encargado de aplicar la logica de negocio a los partidos antes de persistirlos en la base de datos
 * o de devolver dichas entradas desde la base de datos, es necesario que contenga el spring prototype @Service para su funcionamiento
 * ya que es la forma de declarar al momento de la inyeccion de dependencias que se trata de un service
 */
public interface PartidoService {

	List<PartidoDTO> listAll(); // Obtiene el listado de todos los partidos

	PartidoDTO getById(Long id); // Obtiene el partido de id recibido

	void delete(Long id);

    PartidoDTO save(PartidoDTO partido);

	PartidoDTO update(PartidoDTO partido);

	void initGame(Long id); // Inicia el partido del ID recibido


	PartidoDTO sumarPuntos(Long id, ModoJugador modo); // Suma puntos al modo de jugador recibido del partido del id recibido
}
package com.leo.tennis.service;



import java.util.List;

import com.leo.tennis.dto.JugadorDTO;

/**
 * <p>Service de Jugador</p>

 * Este componente sera el encargado de aplicar la logica de negocio a los jugadores antes de persistirlos en la base de datos
 * o de devolver dichas entradas desde la base de datos, es necesario que contenga el spring prototype @Service para su funcionamiento
 * ya que es la forma de declarar al momento de la inyeccion de dependencias que se trata de un service
 */
public interface JugadorService {

	List<JugadorDTO> listAll();

	JugadorDTO getById(Long id); // Obtiene el jugador de id recibido

	JugadorDTO save(JugadorDTO jugador);

	JugadorDTO update(JugadorDTO jugador);

	void delete(Long id);

	JugadorDTO recalculateRanking(Long id);// ranking 

}

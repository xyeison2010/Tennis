package com.leo.tennis.service;

import java.util.List;

import com.leo.tennis.dto.TorneoDTO;



public interface TorneoService {

	List<TorneoDTO> listAll(); // Obtiene el listado de todos los partidos

	TorneoDTO getById(Long id); // Obtiene el partido de id recibido
	
	
	TorneoDTO save(TorneoDTO torneoDto);
}

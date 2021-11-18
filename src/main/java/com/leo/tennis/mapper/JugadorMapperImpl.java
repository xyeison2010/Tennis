package com.leo.tennis.mapper;



import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.model.Jugador;

@Component
public class JugadorMapperImpl implements JugadorMapper {
@Autowired
private EntrenadorMapper entrenadorMapper ;

	
	@Override
	public JugadorDTO toDTO(Jugador entity) {
		if (entity == null) {// si no se encuentra vota null
			return null;
		}

		//pasamos valores por defecto
		JugadorDTO jugadorDTO = new JugadorDTO();

		jugadorDTO.setId(entity.getId());
		jugadorDTO.setNombre(entity.getNombre());
		jugadorDTO.setPuntos(entity.getPuntos());
		jugadorDTO.setEntrenador(entrenadorMapper.toDTO(entity.getEntrenador()));
		jugadorDTO.setGanancia(entity.getGanancia()); // agrege ganancia

		return jugadorDTO;
	}

	@Override
	// tipo de salida   ,y tipo de entrada
	public Jugador fromDTO(JugadorDTO entityDTO) {
		
		if (entityDTO == null) {
			return null;
		}
		
//y con la entidad principal recibimos los valores por defecto
		Jugador jugador = new Jugador();

		jugador.setId(entityDTO.getId());
		jugador.setNombre(entityDTO.getNombre());
		jugador.setPuntos(entityDTO.getPuntos());
		jugador.setEntrenador(entrenadorMapper.fromDTO(entityDTO.getEntrenador()));
		jugador.setGanancia(entityDTO.getGanancia());
		return jugador;
	}
}

package com.leo.tennis.mapper;

import org.springframework.stereotype.Component;

import com.leo.tennis.dto.EntrenadorDTO;
import com.leo.tennis.model.Entrenador;

//no olvidar debe llevar @component
@Component
public class EntrenadorMapperImpl implements  EntrenadorMapper{

	@Override
	public EntrenadorDTO toDTO(Entrenador entity) {
		if(entity ==  null) {
			return null;
		}
		EntrenadorDTO   entrenadorDTO = new EntrenadorDTO();
		entrenadorDTO.setId(entity.getId());
		entrenadorDTO.setNombre(entity.getNombre());

		return entrenadorDTO;
	}

	@Override
	public Entrenador fromDTO(EntrenadorDTO entityDTO) {
		if (entityDTO == null) {
			return null ;
		}
		Entrenador entrenador = new Entrenador();
		entrenador.setId(entityDTO.getId());
		entrenador.setNombre(entityDTO.getNombre());
	
		return entrenador;
	}

}

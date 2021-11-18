package com.leo.tennis.mapper;

import com.leo.tennis.dto.EntrenadorDTO;
import com.leo.tennis.model.Entrenador;

//pasamos.dtoafrom
public interface EntrenadorMapper {
	EntrenadorDTO toDTO(Entrenador entity);//de To 
	Entrenador fromDTO(EntrenadorDTO entityDTO);// va hacia From
	
}

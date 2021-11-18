package com.leo.tennis.mapper;

import com.leo.tennis.dto.TorneoDTO;
import com.leo.tennis.model.Torneo;

public interface TorneoMapper {

	TorneoDTO toDTO (Torneo entity);
	Torneo fromDTO (TorneoDTO entityDTO);
}

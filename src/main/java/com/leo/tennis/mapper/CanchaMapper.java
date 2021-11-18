package com.leo.tennis.mapper;

import com.leo.tennis.dto.CanchaDTO;
import com.leo.tennis.model.Cancha;

public interface CanchaMapper {

    CanchaDTO toDTO(Cancha entity);//de To 
    Cancha fromDTO(CanchaDTO entityDTO);// va hacia From

 
}

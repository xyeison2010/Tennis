package com.leo.tennis.mapper;

import org.springframework.stereotype.Component;

import com.leo.tennis.dto.CanchaDTO;
import com.leo.tennis.model.Cancha;

@Component
public class CanchaMapperImpl implements CanchaMapper {
	@Override
	public CanchaDTO toDTO(Cancha entity) {
		if(entity == null) {
			return null;
		}
		CanchaDTO canchaDTO = new CanchaDTO();
		canchaDTO.setId(entity.getId());
		canchaDTO.setNombre(entity.getNombre());
		canchaDTO.setDireccion(entity.getDireccion());
		
		return canchaDTO ;
	}


    @Override
    public Cancha fromDTO(CanchaDTO entityDTO) {
        if ( entityDTO == null ) {
            return null;
        }

        Cancha cancha = new Cancha();

        cancha.setId( entityDTO.getId() );
        cancha.setNombre( entityDTO.getNombre() );
        cancha.setDireccion( entityDTO.getDireccion() );

        return cancha;
    }

}

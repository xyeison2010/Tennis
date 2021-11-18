package com.leo.tennis.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leo.tennis.dto.TorneoDTO;
import com.leo.tennis.model.Torneo;



@Component
public class TorneoMapperImpl implements  TorneoMapper{
     @Autowired
	private JugadorMapper jugadorMapper;

     @Autowired
 	private CanchaMapper canchaMapper;
     
	@Override
	public TorneoDTO toDTO(Torneo entity) {
	     if ( entity == null ) {
	            return null;
	        }
	      //pasamos valores por defecto
	     TorneoDTO torneoDTO = new TorneoDTO();

	     torneoDTO.setId( entity.getId() );
	     torneoDTO.setJugador1(jugadorMapper.toDTO(entity.getJugador1()));
	     torneoDTO.setJugador2(jugadorMapper.toDTO(entity.getJugador2()));
	     torneoDTO.setJugador3(jugadorMapper.toDTO(entity.getJugador3()));
	     torneoDTO.setJugador4(jugadorMapper.toDTO(entity.getJugador4()));
	   
	     torneoDTO.setFechaComienzo(entity.getFechaComienzo());
	     torneoDTO.setEstado(entity.getEstado());
	     torneoDTO.setCancha( canchaMapper.toDTO( entity.getCancha() ) );
	
	        return torneoDTO;
}
	
    @Override
	public Torneo fromDTO(TorneoDTO entityDTO) {
    	if ( entityDTO == null ) {
            return null;
        }
    	Torneo torneo = new Torneo();

    	torneo.setId( entityDTO.getId() );
    	torneo.setJugador1(jugadorMapper.fromDTO(entityDTO.getJugador1()));
    	torneo.setJugador2(jugadorMapper.fromDTO(entityDTO.getJugador2()));
    	torneo.setJugador3(jugadorMapper.fromDTO(entityDTO.getJugador3()));
    	torneo.setJugador4(jugadorMapper.fromDTO(entityDTO.getJugador4()));
    	
    	torneo.setFechaComienzo(entityDTO.getFechaComienzo());
    	torneo.setEstado(entityDTO.getEstado());
    	torneo.setCancha( canchaMapper.fromDTO( entityDTO.getCancha() ) );
	
	        return torneo;
	}

}

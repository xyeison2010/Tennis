package com.leo.tennis.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.leo.tennis.dto.PartidoDTO;
import com.leo.tennis.model.Partido;
//NO OLVIDAR PONER @COMPONENT
@Component
public class PartidoMapperImpl implements PartidoMapper {

	//como entidad partido tiene joincolumns , llamamos y inyectamos autowired
	@Autowired
    private JugadorMapper jugadorMapper;
	
	@Autowired
	private CanchaMapper canchaMapper;

	/*---- esto sirve solo para testear --------- */
    public PartidoMapperImpl(CanchaMapper canchaMapper, JugadorMapper jugadorMapper) {
    	  this.canchaMapper = canchaMapper;
          this.jugadorMapper = jugadorMapper;
	} 
    /*   ----------------------------------------*/


	@Override
    public PartidoDTO toDTO(Partido entity) {
        if ( entity == null ) {
            return null;
        }
      //pasamos valores por defecto
        PartidoDTO partidoDTO = new PartidoDTO();

        partidoDTO.setId( entity.getId() );
        partidoDTO.setFechaComienzo( entity.getFechaComienzo() );
        partidoDTO.setEstado( entity.getEstado() );
        partidoDTO.setJugadorLocal( jugadorMapper.toDTO( entity.getJugadorLocal() ) );
        partidoDTO.setJugadorVisitante( jugadorMapper.toDTO( entity.getJugadorVisitante() ) );
        partidoDTO.setScoreLocal( entity.getScoreLocal() );
        partidoDTO.setPuntosGameActualLocal( entity.getPuntosGameActualLocal() );
        partidoDTO.setCantidadGamesLocal( entity.getCantidadGamesLocal() );
        partidoDTO.setScoreVisitante( entity.getScoreVisitante() );
        partidoDTO.setPuntosGameActualVisitante( entity.getPuntosGameActualVisitante() );
        partidoDTO.setCantidadGamesVisitante( entity.getCantidadGamesVisitante() );
        partidoDTO.setCancha( canchaMapper.toDTO( entity.getCancha() ) );

        return partidoDTO;
    }

    
    @Override
      // tipo de salida   ,y tipo de entrada
    public Partido fromDTO(PartidoDTO entityDTO) {
        if ( entityDTO == null ) {
            return null;
        }

        Partido partido = new Partido();
      //y con la entidad principal recibimos los valores por defecto
        
        partido.setId( entityDTO.getId() );
        partido.setFechaComienzo( entityDTO.getFechaComienzo() );
        partido.setEstado( entityDTO.getEstado() );
        partido.setJugadorLocal( jugadorMapper.fromDTO( entityDTO.getJugadorLocal() ) );
        partido.setJugadorVisitante( jugadorMapper.fromDTO( entityDTO.getJugadorVisitante() ) );
        partido.setScoreLocal( entityDTO.getScoreLocal() );
        partido.setPuntosGameActualLocal( entityDTO.getPuntosGameActualLocal() );
        partido.setCantidadGamesLocal( entityDTO.getCantidadGamesLocal() );
        partido.setScoreVisitante( entityDTO.getScoreVisitante() );
        partido.setPuntosGameActualVisitante( entityDTO.getPuntosGameActualVisitante() );
        partido.setCantidadGamesVisitante( entityDTO.getCantidadGamesVisitante() );
        partido.setCancha( canchaMapper.fromDTO( entityDTO.getCancha() ) );

        return partido;
    }
}

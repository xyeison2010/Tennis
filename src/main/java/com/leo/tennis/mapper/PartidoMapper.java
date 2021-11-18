package com.leo.tennis.mapper;

import com.leo.tennis.dto.PartidoDTO;
import com.leo.tennis.model.Partido;

//<p>Componente de Mapper</p>
//* hay q hacer mapeo es,pasar los valores del jugadorDTo q esta en el formulario al jugador encontrado a la bd 
//* esto nos servira como reutilizacion.
//* pero como usamos servicios rest backend , por defecto debemos integrar mapper y @component
public interface PartidoMapper {

    PartidoDTO toDTO(Partido entity); //de To 
    Partido fromDTO(PartidoDTO entityDTO);// va hacia From

}

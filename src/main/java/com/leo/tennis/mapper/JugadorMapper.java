package com.leo.tennis.mapper;

import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.model.Jugador;

//<p>Componente de Mapper</p>
//* hay q hacer mapeo es,pasar los valores del jugadorDTo q esta en el formulario al jugador encontrado a la bd 
//* esto nos servira como reutilizacion.
//* pero como usamos servicios rest backend , por defecto debemos integrar mapper y @component

public interface JugadorMapper {


JugadorDTO toDTO(Jugador entity); //de To 
Jugador fromDTO (JugadorDTO entityDTO);// va hacia From


}

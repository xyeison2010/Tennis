import React, { useState } from 'react';
import Typography from '../../components/Typography/Typography';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import httpClient from '../../lib/httpClient';

const JugarPartido = (props) => {
  const [partido, setPartido] = useState(props.location.state.partido);
  const sumarPunto = async (idPartido, modoJugador) => {
    try {
      let response = await httpClient.post(`/partidos/${idPartido}/actions/sumar-punto`, { params : {modoJugador} });
      setPartido(response);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <Typography>Jugar Partido</Typography>
      <Table striped bordered hover>
        <thead>
        <tr>
          <th>Jugadores</th>
          <th>Puntos</th>
          <th>Games</th>
          { partido.estado !== 'FINALIZADO' ? <th>Acción</th> : null }
       <th> Ganancia </th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>{partido.jugadorLocal.nombre}</td>
          <td>{partido.puntosGameActualLocal === null ? 0 : partido.puntosGameActualLocal}</td>
          <td>{partido.cantidadGamesLocal}</td>
          {partido.estado !== 'FINALIZADO' ?
            <td>
            <Button variant='success' onClick={() => sumarPunto(partido.id, 'LOCAL')}>
              Sumar punto
            </Button>
            </td>: null}
            <td>{partido.jugadorLocal.ganancia}</td>
        </tr>
        <tr>
          <td>{partido.jugadorVisitante.nombre}</td>
          <td>{partido.puntosGameActualVisitante === null ? 0 : partido.puntosGameActualVisitante}</td>
          <td>{partido.cantidadGamesVisitante}</td>
          {partido.estado !== 'FINALIZADO' ?
            <td>
              <Button variant='success' onClick={() => sumarPunto(partido.id, 'VISITANTE')}>
                Sumar punto
              </Button>
            </td> : null}
            <td>{partido.jugadorVisitante.ganancia}</td>
        </tr>
        </tbody>
      </Table>
    </>
  );
};

export default JugarPartido;

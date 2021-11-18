import React from 'react';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';

const TableBody = (data) => {
  return data.dataForTable.map((itemRow, index) => {
    return (
      <tr key={`tr-${index}`}>
        <td>{itemRow.id}</td>
        <td>{itemRow.fechaComienzo}</td>
        <td>{itemRow.estado}</td>
        <td>{itemRow.jugador1.nombre}</td>
        <td>{itemRow.jugador2.nombre}</td>
        <td>{itemRow.jugador3.nombre}</td>
        <td>{itemRow.jugador4.nombre}</td>
        <td>{itemRow.cancha.nombre}</td>
        <td>
          {itemRow.estado === "NO_INICIADO" ? <Button onClick={(event) => data.editTorneo(itemRow, event)} variant="primary">Editar</Button> : ''}
          {itemRow.estado === "NO_INICIADO" ? <Button onClick={(event) => data.deleteTorneo(itemRow.id, event)} variant="danger"> Eliminar </Button> : ''}
          <Link
            to={{
              pathname: '/partido/jugar-partido', //falta cambiar
              state: { partido: itemRow },
            }}
          >
            <Button variant="success" onClick={() => data.iniciarPartido(itemRow.id)}>{itemRow.estado === 'FINALIZADO' ? 'Ver' : 'Jugar Torneo'}</Button>
          </Link>
        </td>
      </tr>
    );
  });
};

const TableTorneo = (props) => {
  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>Id</th>
          <th>Fecha Comienzo</th>
          <th>Estado</th>
          <th>Jugador 1</th>
          <th>Jugador 2</th>
          <th>Jugador 3</th>
          <th>Jugador 4</th>
          <th>Cancha</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>{TableBody(props)}</tbody>
    </Table>
  );
};

export default TableTorneo;
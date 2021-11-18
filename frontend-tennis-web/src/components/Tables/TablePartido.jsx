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
        <td>{itemRow.jugadorLocal.nombre}</td>
        <td>{itemRow.jugadorVisitante.nombre}</td>
        <td>{itemRow.cancha.nombre}</td>
        <td>
          {itemRow.estado === "NO_INICIADO" ? <Button onClick={(event) => data.editPartido(itemRow, event)} variant="primary">Editar</Button> : ''}
          {itemRow.estado === "NO_INICIADO" ? <Button onClick={(event) => data.deletePartido(itemRow.id, event)} variant="danger"> Eliminar </Button> : ''}
          <Link
            to={{
              pathname: '/partido/jugar-partido',
              state: { partido: itemRow },
            }}
          >
            <Button variant="success" onClick={() => data.iniciarPartido(itemRow.id)}>{itemRow.estado === 'FINALIZADO' ? 'Ver' : 'Jugar Partido'}</Button>
          </Link>
        </td>
      </tr>
    );
  });
};

const TablePartido = (props) => {
  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>Id</th>
          <th>Fecha Comienzo</th>
          <th>Estado</th>
          <th>Jugador Local</th>
          <th>Jugador Visitante</th>
          <th>Cancha</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>{TableBody(props)}</tbody>
    </Table>
  );
};

export default TablePartido;

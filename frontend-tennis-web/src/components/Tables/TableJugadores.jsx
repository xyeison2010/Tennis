import React from 'react';
import Table from 'react-bootstrap/Table';
import Button from 'react-bootstrap/Button';

const TableBody = (data) => {//pasamos data es como una props
    //data.dataFortable es sin destructurar 
    return data.dataForTable.map(  (itemRow) => {//.map itera elementoXelemnto del array 
        return (
            <tr key={`tr-${itemRow.id}`}> 
                <td>{itemRow.id}</td>
                <td>{itemRow.nombre}</td>
                <td>{itemRow.puntos}</td>
               <td>{itemRow.entrenador.nombre}</td>
                <td>
                    <Button onClick={event => data.detail(itemRow, event)} variant="info">Info</Button>{' '}
                    <Button onClick={event => data.edit(itemRow, event)} variant="primary">Editar</Button>{' '}
                    <Button onClick={event => data.delete(itemRow.id, event)} variant="danger">Eliminar</Button>{' '}
                    <Button onClick={event => data.recalcularRanking(itemRow.id, event)} variant="success">Recalcular Ranking</Button>
                </td>
                <td>{itemRow.ganancia}</td>
            </tr>//pasa la informacion del itemRow ,y el evento 
        )//agrege ganancia
    })
}

const TableJugadores = (props) => {
    return (
        <Table striped bordered hover responsive="sm">
            <thead>
            <tr>
                <th>Id</th>
                <th>Nombre</th>
                <th>Puntos</th>
                <th>Entrenador</th>
                <th>Acciones</th>
                <th>Ganancia Total</th>
            </tr>
            </thead>
            <tbody>
            {TableBody(props)}
            </tbody>
        </Table>
    )
}

export default TableJugadores;

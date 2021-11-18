import React from 'react';
import Table from 'react-bootstrap/Table';

const generateHeaderTable = (columns) => (<tr>{columns.map((head, index) => <th key={`th-${index}`}>{head}</th>)}</tr>)

const generateContentTable = (data) => {
    return data.map((item, index) => {
       return <tr key={`tr-${index}`}>{ generateCell(item) }</tr>;
    });
}

const generateCell = (data) => {
    return Object.keys(data).map((key, index) => {
        return <td key={`td-${index}`}>{ data[key] }</td>
    })
}

const Tables = (props) => {
    const tableHead = props.dataForTable.headers;
    const tableBody = props.dataForTable.body;

    return (
        <Table striped bordered hover>
            <thead>
                {generateHeaderTable(tableHead)}
            </thead>
            <tbody>
                { generateContentTable(tableBody) }
            </tbody>
        </Table>
    )

}

export default Tables;

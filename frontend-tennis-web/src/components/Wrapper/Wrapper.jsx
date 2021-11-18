import React from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import Menu from "../Menu/Menu";

//se procede hacer un componente funcional
//wrapper es es a linea blanca con los nomrbres home,parido.jugador odo eso 
// yy gracias a children cambiamos su info ,cuando dirigimos a otra pesttaña
//en conclusion este componente lo reutilizamos solo para eso
const Wrapper = (props) => {
    const { children } = props;
    return (
        <>
            <Menu/>
            <Container>
                <Row>
                    <Col>
                        {children}
                    </Col>
                </Row>
            </Container>
        </>
    );
}
//ese container es de react boostrap
export default Wrapper;


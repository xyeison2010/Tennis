import React from 'react';
import { Link } from 'react-router-dom';
import {Nav, Navbar} from 'react-bootstrap';
//el menu es el navbar  y esta adentro de wrapper
const Menu = () => {
    return (
        <>
            <Navbar collapseOnSelect expand="md" bg="dark" variant="dark">
                <div className="container">
                    <Navbar.Brand>Baufest Tennis</Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="mr-auto">
                            <Nav.Link as={Link} to="/">Home</Nav.Link>
                            <Nav.Link as={Link} to="/jugador">Jugador</Nav.Link>
                            <Nav.Link as={Link} to="/partido">Partido</Nav.Link>
                            <Nav.Link as={Link} to="/cancha">Cancha</Nav.Link>
                            <Nav.Link as={Link} to="/about">About</Nav.Link>
                            <Nav.Link as={Link} to="/torneo">Torneo</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </div>
            </Navbar>
        </>
    );
}
// un booleano collapseOnSelect
export default Menu;

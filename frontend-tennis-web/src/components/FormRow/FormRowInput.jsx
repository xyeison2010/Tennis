import React from "react";
import { Form, Col } from "react-bootstrap";

const FormRowInput = (props) => {
    const { label, type, required, placeholder, property, value, handleChange } = props;
    return (
        <Form.Group controlId={property}>
            <Form.Row>
                <Col className={'col-md-3'}>
                    <Form.Label>{label}</Form.Label>
                </Col>
                <Col className={'col-md-9'}>
                    <Form.Control
                        noValidate //este pa q no valide del navegador
                        type={type}
                        required={required}
                        placeholder={placeholder}
                        value={value}
                        onChange={(e) => handleChange(property, e.target.value)}//el evento onchange es el q permite q cada vez haya cambio dentro del input dispare una funcion
                    //e.target.value evento de js 
                    />
                    {required && (
                        <Form.Control.Feedback type={"invalid"}>
                            {label} es requerido
                        </Form.Control.Feedback>
                    )}
                </Col>
            </Form.Row>
        </Form.Group>
    )
}

export default FormRowInput;

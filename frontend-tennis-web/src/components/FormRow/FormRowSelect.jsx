import React from 'react';
import { Col, Form } from 'react-bootstrap';


const FormRowSelect = (props) => {
  const { property, required, label, value, handleChange, placeholder, options } = props;
// el select se define como option en react boostrap 
  return (
    <Form.Group controlId={property}>
      <Form.Row>
        <Col className='col-md-3'>
          <Form.Label>{label}</Form.Label> 
        </Col>
        <Col className='col-md-9'>
          <Form.Control
            noValidate
            as='select'
            placeholder={placeholder}
            value={value}
            required={required}
            onChange={(e) => handleChange(property, e.target.value)}

          >
            <option value=''>{placeholder}</option>
            {options}
          </Form.Control>
          <Form.Control.Feedback type='invalid'>
            {label} es requerido.
          </Form.Control.Feedback>
        </Col>
      </Form.Row>
    </Form.Group>
  );
};
//dentro de Form.Group hay 3 properties importantes :
//Form.Label : nombre del campo
//From.Control : el tipo de input campo q se usa , la validacion 
//From.Control.Feedback : con el mensaje para si se activa la validacion
export default FormRowSelect;

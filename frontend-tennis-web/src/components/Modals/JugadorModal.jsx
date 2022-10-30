import React, { useRef } from 'react';
import { Form, Button, Modal } from 'react-bootstrap';
import FormRowInput from "../FormRow/FormRowInput";
import FormRowSelect from '../FormRow/FormRowSelect';
//los componentes son cuando se quieren reutilizar muchas veces un codigo

const JugadorModal = (props) => {
    const formRef = useRef(null);//hook useRef es para referencia un compononente padre a hijo
    //este componente hijo recibe las props
    const {
         show, 
        onHide, 
        isEdit, 
        handleChange, 
        jugador, 
        validated,
        handleSubmit, 
        errorMsg, 
        entrenadoresList,     
        } = props;//esto es una destructuracion


//y para buscar el id , de nuestros joinColumns del backend ,hacemos esto 
     const entrenadores = entrenadoresList.map((entrenador) => {
           return (
             <option key={entrenador.id} value={parseInt(entrenador.id)}>
               {entrenador.nombre}
             </option>
           );
         });
  
    return (
        <Modal    
            show={show}
            onHide={onHide}
            centered={true} 
            backdrop="static" 
            keyboard={false}  
        >
            <Modal.Header closeButton>
                <Modal.Title>{isEdit ? 'Editar' : 'Agregar'} Jugador</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <Form className={"form"} noValidate validated={validated} ref={formRef}>
                    <FormRowInput
                        label={'Nombre'}
                        type={'text'}
                        required={true}
                        placeholder={'Nombre del jugador'}
                        property={'nombre'}
                        value={jugador.nombre}
                        handleChange={handleChange}
                    />
                    <FormRowInput
                        label={'Puntos'}
                        type={'number'}
                        required={true}
                        placeholder={'Puntos del jugador'}
                        property={'puntos'}
                        value={jugador.puntos}
                        handleChange={handleChange}
                    />
                    <FormRowSelect
                        label={ 'Entrenador'}
                        property={ 'entrenador'}
                        required={true }
                        value={jugador.entrenador.id }//este entrenador.id viene de valor inicial
                        handleChange={ handleChange}
                        placeholder={'selecciona entrenador' }
                        options={entrenadores }
                    />


                    {errorMsg !== '' &&
                        <Form.Group className="alert-danger">
                            {errorMsg}
                        </Form.Group>
                    }
                </Form>
            </Modal.Body>

            <Modal.Footer>
                <Button onClick={onHide} variant="danger">Cancelar</Button>
                <Button onClick={(e) => handleSubmit(e, formRef.current, isEdit)} variant="success">{isEdit ? 'Editar' : 'Agregar'}</Button>
            </Modal.Footer>
        </Modal>
    )
}

export default JugadorModal;

import React, { useEffect, useState } from 'react';
import Typography from '../../components/Typography/Typography';
import { Button } from 'react-bootstrap';
import TableJugadores from '../../components/Tables/TableJugadores';
import JugadorModal from '../../components/Modals/JugadorModal';
import httpClient from '../../lib/httpClient';//importante importar 
//una variable mockear,array,que tiene listas con objetos
let jugadoresListMock = [
  { "id": 1, "nombre": "pepe", "puntos": 1000 },
  { "id": 2, "nombre": "jose", "puntos": 100 },
  { "id": 3, "nombre": "saul", "puntos": 50 },
  { "id": 4, "nombre": "anibal", "puntos": 50 }
];


//colocamos por  defecto a nuestros joinColumns estos valores iniciales
const jugadorInit = {
  nombre: '',
  puntos: 0,
  entrenador: {//importante agregar esto si nuestra entity tiene joinColums, pq si no esta saldra error
    id: -1,
  },
  ganancia: 0,
};

const Jugador = (props) => {
  const [jugadoresList, setJugadoresList] = useState([]);
  const [jugadorData, setJugadorData] = useState(jugadorInit);

  const [isEdit, setIsEdit] = useState(false);
  const [hasErrorInForm, setHasErrorInForm] = useState(false);
  const [openModal, setOpenModal] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');
  const [entrenadoresList, setEntrenadoresList] = useState([]);

  useEffect(async () => {
    await getJugadores();
    await getEntrenadores();
  }, []);



  //obtener todos los jugadores de la base de datos
  const getJugadores = async () => {
    try {
      const data = await httpClient.get('/jugadores');
      setJugadoresList(data);
    } catch (error) {
      console.log(error);
    }
  };

  const getEntrenadores = async () => {
    try {
      const data = await httpClient.get('/entrenadores')
      setEntrenadoresList(data); 
    } catch (error) {
      console.log(error);
    }
  };


  //post
  const agregarJugador = async () => {
    try {
      const data = await httpClient.post(`/jugadores`, { data: jugadorData });
      setJugadoresList([...jugadoresList, data]); 
    } catch (error) {
      console.log(error);
    }
    handleCloseModal();
  };


  //comillas simples invertidas
  const editarJugador = async (id) => {
    try {
   
      const data = await httpClient.put(`/jugadores/${id}`, { data: jugadorData });
      
      setJugadoresList(jugadoresList.map((item) => (item.id === id ? data : item)));
    } catch (error) {
      console.log(error);
    }
    handleCloseModal();
  };
  //borrar 
  const borrarJugador = async (id) => {
    try {
      await httpClient.delete(`/jugadores/${id}`);
 
      setJugadoresList(jugadoresList.filter((jugador) => jugador.id !== id));//devuelve una lista solo con mi condicion
    } catch (error) {
      console.log(error);
    }
  };

  // boton para enviar informacion, data  y evento  al detalle del usuario 
  const handleDetail = (data, event) => {
    event.preventDefault();
    props.history.push(`/jugador/detalle/${data.id}`, { data });//history.push envia codigo a otra pantalla es prop de router react
  };

  const handleEdit = (editData, event) => {
    event.preventDefault();
    handleOpenModal(true, editData);
  };

  const handleDelete = async (id, event) => {//asincrono
    event.preventDefault();//importane para evitar llamadas innecesarias
    if (window.confirm('Estas seguro?')) {
      await borrarJugador(id);
    }
  };
  /*                                                                                    */

  const handleRecalculateRanking = async (id, event) => {
    event.preventDefault();
    await httpClient.put(`/jugadores/${id}/actions/recalculateRanking`);
    const data = await httpClient.get(`/jugadores/${id}`);
    setJugadoresList(jugadoresList.map((item) => (item.id === id ? data : item)));
  };

  // Modal,automaticamente se abre componente JugadorModal
  const handleOpenModal = (editarJugador = false, jugadorToEdit = null) => {
    setIsEdit(editarJugador);

    if (editarJugador) {
      setJugadorData(jugadorToEdit);
    }

    setOpenModal(true);
  };
  //necesito enviarlo por props a mi componente ,jugadormodal
  const handleCloseModal = () => {
    setOpenModal(false);
    setIsEdit(false);
    setHasErrorInForm(false);
    setJugadorData(jugadorInit);
    setErrorMsg('');
  };

  const handleChangeInputForm = (property, value) => {
    value === '' ? setHasErrorInForm(true) : setHasErrorInForm(false);
    const newData = { ...jugadorData };
    switch (property) { //... lleva todas las  variable
      case 'entrenador':
        newData.entrenador = entrenadoresList.filter((x) => x.id === parseInt(value))[0];
        break;

      case 'nombre':
        newData.nombre = value;
        break;

      case 'puntos':
        newData.puntos = value;
        break;

        case 'ganancia':
          newData.ganancia = value;
          break;

      default:
        break;
    }
    setJugadorData(newData);
  };



  const handleSubmitForm = (e, form, isEdit) => {
    e.preventDefault();
    setHasErrorInForm(true);
    //agregar jugador declarado arriba
    if (form.checkValidity()) isEdit ? editarJugador(jugadorData.id) : agregarJugador();
  };

  // API

  return (
    <>
      <Typography id={'title-id'}>Jugador</Typography>

      <div className="mb-2">
        <Button variant="success" onClick={() => handleOpenModal()}>Agregar jugador</Button>
      </div>

      <TableJugadores
        dataForTable={jugadoresList}
        detail={handleDetail}
        edit={handleEdit}
        delete={(id, event) => handleDelete(id, event)}
        recalcularRanking={handleRecalculateRanking}
      />

      <JugadorModal
        show={openModal}
        onHide={handleCloseModal}
        isEdit={isEdit}
        handleChange={handleChangeInputForm}
        jugador={jugadorData}
        validated={hasErrorInForm}
        handleSubmit={handleSubmitForm}
        errorMsg={errorMsg}
        entrenadoresList={entrenadoresList}
      />
    </>
  );
};

export default Jugador;

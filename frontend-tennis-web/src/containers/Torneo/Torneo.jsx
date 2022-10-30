import React, { useEffect, useState } from 'react';
import Typography from '../../components/Typography/Typography';
import TorneoModal from '../../components/Modals/TorneoModal';
import { Button } from 'react-bootstrap';
import httpClient from '../../lib/httpClient';
import TableTorneo from '../../components/Tables/TableTorneo';
//02/11/2021 10:00	
//colocamos por  defecto a nuestros joinColumns estos valores iniciales
const torneoInit = {
  fechaComienzo: '',
  estado: 'NO_INICIADO',
  jugador1: {
    id: -1,
  },
  jugador2: {
    id: -1,
  },
  jugador3: {
    id: -1,
  },
  jugador4: {
    id: -1,
  },
  cancha: {
    id: -1,
  },
 
};
const dateOptions = {
  year: 'numeric',
  month: 'numeric',
  day: 'numeric',
  hour: 'numeric',
  minute: 'numeric',
  hour12: false,
};

const Torneo = () => {
 
  const [torneosList, setTorneosList] = useState([]);
  const [listaJugadores, setListaJugadores] = useState([]);
  const [listaCanchas, setListaCanchas] = useState([]);

  const [torneoData, setTorneoData] = useState(torneoInit);
  const [isEdit, setIsEdit] = useState(false);
  const [hasErrorInForm, setHasErrorInForm] = useState(false);
  const [openModal, setOpenModal] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');
 
 useEffect (async () => {
  await getJugadores();
  await getCanchas();
  await getTorneos();
 },[]);


  const getTorneos = async () => {
    try {
      const data = await httpClient.get('/torneos');//url del backend
      data.map((torneo) => { 
        torneo.fechaComienzo = new Date (torneo.fechaComienzo).toLocaleDateString('es-AR', dateOptions);
        return torneo;
      });

      setTorneosList(data);
    } catch (error) {
      console.log(error);
    }
  };
  const getJugadores = async () => {
    try {
      const data = await httpClient.get('/jugadores');
      setListaJugadores(data);
    } catch (error) {
      console.log(error);
    }
  };

  const getCanchas = async () => {
    try {
      const data = await httpClient.get('/canchas');
      setListaCanchas(data);
    } catch (error) {
      console.log(error);
    }
  };

  const agregarTorneo = async () => {
    try {
      const dataSend = {... torneoData};
      dataSend.fechaComienzo = stringToDate(dataSend.fechaComienzo);
      const data = await httpClient.post('/torneos', { data: dataSend });//save controller
      data.fechaComienzo = new Date (data.fechaComienzo).toLocaleDateString('es-AR', dateOptions);
      setTorneosList([...torneosList, data]);
    } catch (error) {
      console.log(error);
    }
    handleCloseModal();
  };
//iniciar
  const iniciarTorneo = async(id) => {//el .filter es como el ternario pero solo devuelve una condicion
    const torneo = torneosList.filter(element => element.id === id);
    if(torneo[0].estado === 'NO_INICIADO'){
      try{
        await httpClient.put(`/torneos/${id}/actions/init`);
      }
      catch(error){
        console.log(error);
      }
    }
  }

  // Buttons
  const handleEditTorneo = (editData, event) => {
    event.preventDefault();
    handleOpenModal(true, editData);
  };

  const handleDeleteTorneo = async (id, event) => {
    event.preventDefault();
    if (window.confirm('Estas seguro?')) {
      await borrarPartido(id);
    }
  };

   // Modal
   const handleOpenModal = (editarTorneo = false, torneoToEdit = null) => {
    setIsEdit(editarTorneo);

    if (editarTorneo) {
      setTorneoData(torneoToEdit);
    }

    setOpenModal(true);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setIsEdit(false);
    setHasErrorInForm(false);
    setTorneoData(torneoInit);
    setErrorMsg('');
  };
//
  // Form
  const handleChangeInputForm = (property, value) => {
    value === '' ? setHasErrorInForm(true) : setHasErrorInForm(false);

    const newData = { ...torneoData };

    switch (property) {
      case 'jugador1':
        newData.jugador1 = listaJugadores.filter((x) => x.id === parseInt(value))[0];
        break;
      case 'jugador2':
        newData.jugador2 = listaJugadores.filter((x) => x.id === parseInt(value))[0];
        break;
        case 'jugador3':
        newData.jugador3 = listaJugadores.filter((x) => x.id === parseInt(value))[0];
        break;
      case 'jugador4':
        newData.jugador4 = listaJugadores.filter((x) => x.id === parseInt(value))[0];
        break;
      case 'cancha':
        newData.cancha = listaCanchas.filter((x) => x.id === parseInt(value))[0];
        break;
      case 'fechaComienzo':
        newData.fechaComienzo = value;
        break;

      default:
        break;
    }

    setTorneoData(newData);
  };

  const handleSubmitForm = (e, form, isEdit) => {
    e.preventDefault();
    setHasErrorInForm(true);

    if (!validateTorneo()) return;

    if (form.checkValidity()) isEdit ? editarTorneo(torneoData.id) : agregarPartido();
  };

  return (
    <>
      <Typography id={'title-id'}>Torneo</Typography>
      <div className='mb-2'>
        <Button variant='success' onClick={() => handleOpenModal()}>Agregar torneo</Button>
      </div>
     
      <TableTorneo
        dataForTable={torneosList}
       editTorneo={handleEditTorneo}
        deleteTorneo={(id, event) => handleDeleteTorneo(id, event)}
        iniciarTorneo={iniciarTorneo}
      />
       <TorneoModal
        show={openModal}
        onHide={handleCloseModal}
        isEdit={isEdit}
        handleChange={handleChangeInputForm}
        validated={hasErrorInForm}
        handleSubmit={handleSubmitForm}
        errorMsg={errorMsg}
        torneo={torneoData}
        listaJugadores={listaJugadores}
        listaCanchas={listaCanchas}
      />
     
    </>
  );
};

export default Torneo;

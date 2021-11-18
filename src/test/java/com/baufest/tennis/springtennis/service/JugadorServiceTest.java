package com.baufest.tennis.springtennis.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.enums.Estado;
import com.leo.tennis.mapper.JugadorMapperImpl;
import com.leo.tennis.model.Cancha;
import com.leo.tennis.model.Jugador;
import com.leo.tennis.model.Partido;
import com.leo.tennis.repository.JugadorRepository;
import com.leo.tennis.repository.PartidoRepository;
import com.leo.tennis.service.JugadorServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class JugadorServiceTest {
    private final List<Jugador> jugadoresDePrueba = new ArrayList<>();
    private final List<JugadorDTO> jugadoresDTODePrueba = new ArrayList<>();
    private final JugadorDTO jugadorDTOParaAgregar = new JugadorDTO();
    private final Jugador jugadorParaAgregar = new Jugador();
    Cancha cancha = new Cancha("Roland Garros", "Av Francia 123");

    JugadorServiceImpl jugadorService;

    @Mock
    JugadorRepository jugadorRepository;

    @Mock
    PartidoRepository partidoRepository;

    @BeforeEach
    public void setUp() {
        jugadoresDTODePrueba.clear();
        jugadoresDTODePrueba.add(new JugadorDTO());
        jugadoresDTODePrueba.add(new JugadorDTO());
        jugadoresDTODePrueba.add(new JugadorDTO());
        jugadoresDTODePrueba.add(new JugadorDTO());
        jugadoresDTODePrueba.get(0).setNombre("facu");
        jugadoresDTODePrueba.get(1).setNombre("fer");
        jugadoresDTODePrueba.get(2).setNombre("juli");
        jugadoresDTODePrueba.get(3).setNombre("axel");
        jugadoresDTODePrueba.get(0).setId(1L);
        jugadoresDTODePrueba.get(1).setId(2L);
        jugadoresDTODePrueba.get(2).setId(3L);
        jugadoresDTODePrueba.get(3).setId(4L);
        jugadoresDTODePrueba.get(0).setPuntos(20);
        jugadoresDTODePrueba.get(1).setPuntos(15);
        jugadoresDTODePrueba.get(2).setPuntos(10);
        jugadoresDTODePrueba.get(3).setPuntos(5);

        jugadoresDePrueba.clear();
        jugadoresDePrueba.add(new Jugador());
        jugadoresDePrueba.add(new Jugador());
        jugadoresDePrueba.add(new Jugador());
        jugadoresDePrueba.add(new Jugador());
        jugadoresDePrueba.get(0).setNombre("facu");
        jugadoresDePrueba.get(1).setNombre("fer");
        jugadoresDePrueba.get(2).setNombre("juli");
        jugadoresDePrueba.get(3).setNombre("axel");
        jugadoresDePrueba.get(0).setId(1L);
        jugadoresDePrueba.get(1).setId(2L);
        jugadoresDePrueba.get(2).setId(3L);
        jugadoresDePrueba.get(3).setId(4L);
        jugadoresDePrueba.get(0).setPuntos(20);
        jugadoresDePrueba.get(1).setPuntos(15);
        jugadoresDePrueba.get(2).setPuntos(10);
        jugadoresDePrueba.get(3).setPuntos(5);

        jugadorDTOParaAgregar.setId(5L);
        jugadorDTOParaAgregar.setNombre("lucas");
        jugadorDTOParaAgregar.setPuntos(25);

        jugadorParaAgregar.setId(5L);
        jugadorParaAgregar.setNombre("lucas");
        jugadorParaAgregar.setPuntos(25);

        jugadorService = new JugadorServiceImpl(jugadorRepository,partidoRepository,new JugadorMapperImpl());

    }

    @Test
    void testListJugadores() {
        when(jugadorRepository.findAllByOrderByNombreAsc()).thenReturn(jugadoresDePrueba);
        List<JugadorDTO> jugadoresConseguidos = jugadorService.listAll();
        assertEquals(jugadoresDTODePrueba.size(),jugadoresConseguidos.size());
        verify(jugadorRepository,times(1)).findAllByOrderByNombreAsc();
    }

    @Test
    void testGetJugadorByID() {
        when(jugadorRepository.findById(jugadoresDTODePrueba.get(0).getId()))
                .thenReturn(Optional.ofNullable(jugadoresDePrueba.get(0)));
        JugadorDTO jugadorEncontrado = jugadorService.getById(jugadoresDTODePrueba.get(0).getId());
        assertEquals(jugadoresDTODePrueba.get(0).getId(),jugadorEncontrado.getId());
        verify(jugadorRepository).findById(eq(jugadoresDTODePrueba.get(0).getId()));
    }

    @Test
    void testSaveOrUpdate() {
        ArgumentCaptor<Jugador> argumentCaptor = ArgumentCaptor.forClass(Jugador.class);
        when(jugadorRepository.save(argumentCaptor.capture())).thenReturn(jugadorParaAgregar);
        JugadorDTO jugadorDTO = jugadorService.save(jugadorDTOParaAgregar);
        assertEquals(jugadorDTOParaAgregar.getId(),argumentCaptor.getValue().getId());
        assertEquals(jugadorParaAgregar.getId(), jugadorDTO.getId());
        assertEquals(jugadorParaAgregar.getNombre(), jugadorDTO.getNombre());
        verify(jugadorRepository).save(any(Jugador.class));
    }

    @Test
    void testDelete() {
        Long idParaBorrar = 1L;
        when(jugadorRepository.existsById(idParaBorrar)).thenReturn(true);

        jugadorService.delete(idParaBorrar);

        verify(jugadorRepository).existsById(eq(idParaBorrar));
        verify(jugadorRepository).deleteById(eq(idParaBorrar));
    }

    @Test
    void testDeleteNotFound() {
        Long idParaBorrar = 1L;
        when(jugadorRepository.existsById(idParaBorrar)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> jugadorService.delete(idParaBorrar));
        verify(jugadorRepository).existsById(eq(idParaBorrar));
        verify(jugadorRepository,times(0)).deleteById(any());
    }

    @Test
    void testInsertExistent() {
        when(jugadorRepository.existsById(jugadorDTOParaAgregar.getId())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () ->  jugadorService.save(jugadorDTOParaAgregar));
        verify(jugadorRepository).existsById(eq(jugadorDTOParaAgregar.getId()));
    }

    @Test
    void testUpdateExisting() {
        ArgumentCaptor<Jugador> argumentCaptor = ArgumentCaptor.forClass(Jugador.class);
        when(jugadorRepository.existsById(jugadorDTOParaAgregar.getId())).thenReturn(true);
        when(jugadorRepository.save(argumentCaptor.capture())).thenReturn(jugadorParaAgregar);
        JugadorDTO jugadorDTO = jugadorService.update(jugadorDTOParaAgregar);
        assertEquals(jugadorDTOParaAgregar.getId(),argumentCaptor.getValue().getId());
        assertEquals(jugadorParaAgregar.getId(), jugadorDTO.getId());
        assertEquals(jugadorParaAgregar.getNombre(), jugadorDTO.getNombre());
        verify(jugadorRepository,times(1)).save(any(Jugador.class));
        verify(jugadorRepository).existsById(eq(jugadorDTOParaAgregar.getId())); // times por defecto va 1
    }

    @Test
    void testUpdateNotFound() {
        when(jugadorRepository.existsById(jugadorDTOParaAgregar.getId())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () ->  jugadorService.update(jugadorDTOParaAgregar));
        verify(jugadorRepository,times(1)).existsById(eq((jugadorDTOParaAgregar.getId())));
        verify(jugadorRepository,times(0)).save(any());
    }

    @Test
    void testRecalcularRankingJugadorGanoUnPartidoDeLocal() {
        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();
        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 6, 0, 1, cancha));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals(10, jugadorDTO.getPuntos());
    }

    @Test
    void testRecalcularRankingJugadorGanoTresPartidosDeLocalYDosDeVisitante() {
        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();
        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 6, 0, 1, cancha));
        partidos.add(new Partido(2L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(2), 0, 6, 0, 1, cancha));
        partidos.add(new Partido(3L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(3), 0, 6, 0, 1, cancha));
        partidos.add(new Partido(4L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(1), jugador, 0, 0, 0, 6, cancha));
        partidos.add(new Partido(5L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(2), jugador, 0, 0, 0, 6, cancha));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals(60, jugadorDTO.getPuntos());
    }

    @Test
    void testRecalcularRankingJugadorPerdioDosPartidosDeLocal() {
        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();
        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 1, 0, 6, cancha));
        partidos.add(new Partido(2L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(2), 0, 0, 0, 6, cancha));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals(0, jugadorDTO.getPuntos());
    }

    @Test
    void testRecalcularRankingJugadorPerdioDosPartidosDeVisitante() {
        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();
        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(1), jugador, 0, 6, 0, 1, cancha));
        partidos.add(new Partido(2L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(2), jugador, 0, 6, 0, 0, cancha));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals(0, jugadorDTO.getPuntos());
    }

    @Test
    void testRecalcularRankingJugadorGanoDosPartidosDeLocalGanoUnoDeVisitantePerdioTresDeLocalYPerdioDosDeVisitante() {

        Jugador jugador = jugadoresDePrueba.get(0);

        List<Partido> partidos = new ArrayList<>();

        partidos.add(new Partido(1L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 6, 0, 1, null));
        partidos.add(new Partido(2L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(2), 0, 6, 0, 2, null));
        partidos.add(new Partido(3L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(1), jugador, 0, 2, 0, 6, null));
        partidos.add(new Partido(4L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(1), 0, 0, 0, 6, null));
        partidos.add(new Partido(5L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(2), 0, 1, 0, 6, null));
        partidos.add(new Partido(6L, new Date(), Estado.FINALIZADO, jugador, jugadoresDePrueba.get(3), 0, 0, 0, 6, null));
        partidos.add(new Partido(7L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(1), jugador, 0, 6, 0, 0, null));
        partidos.add(new Partido(8L, new Date(), Estado.FINALIZADO, jugadoresDePrueba.get(2), jugador, 0, 6, 0, 1, null));

        when(partidoRepository.findAll()).thenReturn(partidos);
        when(jugadorRepository.findById(1L)).thenReturn(Optional.of(jugador));
        when(jugadorRepository.save(jugador)).thenReturn(jugador);

        JugadorDTO jugadorDTO = this.jugadorService.recalculateRanking(1L);

        assertEquals( 20, jugadorDTO.getPuntos());
    }
}
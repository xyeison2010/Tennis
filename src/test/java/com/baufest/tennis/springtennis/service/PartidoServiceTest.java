package com.baufest.tennis.springtennis.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.leo.tennis.dto.CanchaDTO;
import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.dto.PartidoDTO;
import com.leo.tennis.enums.Estado;
import com.leo.tennis.enums.ModoJugador;
import com.leo.tennis.mapper.CanchaMapperImpl;
import com.leo.tennis.mapper.JugadorMapperImpl;
import com.leo.tennis.mapper.PartidoMapper;
import com.leo.tennis.mapper.PartidoMapperImpl;
import com.leo.tennis.model.Cancha;
import com.leo.tennis.model.Jugador;
import com.leo.tennis.model.Partido;
import com.leo.tennis.repository.PartidoRepository;
import com.leo.tennis.service.PartidoServiceImpl;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartidoServiceTest {

    public static final int SCORE_ADV = 4;
    public static final int SCORE_40 = 3;
    public static final int SCORE_30 = 2;
    public static final int SCORE_15 = 1;
    public static final int SCORE_0 = 0;

    List<Partido> partidosDePrueba = new ArrayList<>();
    List<PartidoDTO> partidosDTODePrueba = new ArrayList<>();

    Jugador jugador1 = new Jugador(1L, "Federer", SCORE_30, null, 0);
    Jugador jugador2 = new Jugador(2L, "Nadal", SCORE_30, null, 0);

    JugadorDTO jugadorDTO1 = new JugadorDTO(1L, "Federer", SCORE_30, null, 0);
    JugadorDTO jugadorDTO2 = new JugadorDTO(2L, "Nadal", SCORE_30, null, 0);//ese null ultimo es de entrenador

    Cancha cancha1 = new Cancha("Roland Garros", "Av Francia 123");
    CanchaDTO canchaDTO1 = new CanchaDTO("Roland Garros", "Av Francia 123");

    Partido partidoParaAgregar = new Partido(1L, new Date(2543447456000L), Estado.NO_INICIADO, jugador1, jugador2, 0, 0, 0, 0, cancha1);

    PartidoDTO partidoDTOParaAgregar = new PartidoDTO(1L, new Date(2543447456000L), Estado.NO_INICIADO, jugadorDTO1, jugadorDTO2, 0, 0, 0, 0, canchaDTO1);

    @Mock()
    PartidoRepository partidoRepository;

    PartidoServiceImpl partidoService;

    PartidoMapper mapper = new PartidoMapperImpl(new CanchaMapperImpl(),new JugadorMapperImpl());

    @BeforeEach
    public void setUp() {
        partidosDePrueba.clear();
        partidosDePrueba.add(new Partido(new Date(), Estado.NO_INICIADO, jugador1, jugador1, cancha1));
        partidosDePrueba.add(new Partido());
        partidosDePrueba.add(new Partido());
        partidosDePrueba.add(new Partido());

        partidosDTODePrueba.clear();
        partidosDTODePrueba.add(new PartidoDTO(new Date(), Estado.NO_INICIADO, jugadorDTO1, jugadorDTO1, canchaDTO1));
        partidosDTODePrueba.add(new PartidoDTO());
        partidosDTODePrueba.add(new PartidoDTO());
        partidosDTODePrueba.add(new PartidoDTO());

        partidoService = new PartidoServiceImpl(partidoRepository,mapper);
    }


    @Test
    void testListPartidos() {
        when(partidoRepository.findAll()).thenReturn(partidosDePrueba);
        List<PartidoDTO> canchasConseguidos = partidoService.listAll();
        assertEquals(partidosDePrueba.size(), canchasConseguidos.size());
        verify(partidoRepository).findAll();
    }

    @Test
    void testGetPartidoByID() {
        when(partidoRepository.findById(partidoParaAgregar.getId()))
                .thenReturn(Optional.of(partidoParaAgregar));
        PartidoDTO partidoEncontrado = partidoService.getById(partidoParaAgregar.getId());
        assertEquals(partidoParaAgregar.getId(), partidoEncontrado.getId());
        verify(partidoRepository).findById(eq(partidoParaAgregar.getId()));
    }

    @Test
    void testSaveOrUpdate() {
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        when(partidoRepository.save(argumentCaptor.capture())).thenReturn(partidoParaAgregar);
        PartidoDTO partidoDTO = partidoService.save(partidoDTOParaAgregar);
        assertEquals(partidoParaAgregar.getId(), argumentCaptor.getValue().getId());
        assertEquals(partidoParaAgregar.getId(), partidoDTO.getId());
        assertEquals(partidoParaAgregar.getJugadorLocal().getId(), partidoDTO.getJugadorLocal().getId());
        assertEquals(partidoParaAgregar.getJugadorVisitante().getId(), partidoDTO.getJugadorVisitante().getId());
        assertEquals(partidoParaAgregar.getFechaComienzo(), partidoDTO.getFechaComienzo());
        verify(partidoRepository).save(any());
    }

    @Test
    void testDelete() {
        Long idParaBorrar = 1L;
        when(partidoRepository.existsById(idParaBorrar)).thenReturn(true);
        when(partidoRepository.findById(idParaBorrar)).thenReturn(Optional.of(partidoParaAgregar));
        partidoService.delete(idParaBorrar);
        verify(partidoRepository).deleteById(eq(idParaBorrar));
    }

    @Test
    void testDeleteNotFound() {
        Long idParaBorrar = 1L;
        when(partidoRepository.existsById(idParaBorrar)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> partidoService.delete(idParaBorrar));
        verify(partidoRepository,times(0)).deleteById(any());
    }

    @Test
    void testDeletePartidoFinalizado() {
        Partido partidoFinalizado = new Partido(new Date(), Estado.FINALIZADO, jugador1, jugador1, cancha1);
        partidoFinalizado.setId(1L);
        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoFinalizado));
        assertThrows(IllegalArgumentException.class, () -> partidoService.delete(1L));
        verify(partidoRepository,times(0)).deleteById(any());
    }

    @Test
    void testDeletePartidoEnCurso() {
        Partido partidoEnCurso = new Partido(new Date(), Estado.EN_CURSO, jugador1, jugador1, cancha1);
        partidoEnCurso.setId(1L);
        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoEnCurso));
        assertThrows(IllegalArgumentException.class, () -> partidoService.delete(1L));
        verify(partidoRepository,times(0)).deleteById(any());
    }

    @Test
    void testInsertExisting() {
        partidoParaAgregar.setId(1L);
        when(partidoRepository.existsById(partidoParaAgregar.getId())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> partidoService.save(partidoDTOParaAgregar));
        verify(partidoRepository,times(0)).save(any());
        verify(partidoRepository,times(1)).existsById(eq(1L));
    }

    @Test
    void testInsertPartidoConJugadoresIgualesEntreSi() {
        PartidoDTO partido = new PartidoDTO(new Date(), Estado.NO_INICIADO, jugadorDTO1, jugadorDTO1, canchaDTO1);
        assertThrows(IllegalArgumentException.class, () -> partidoService.save(partido));
        verify(partidoRepository,times(0)).save(any());
    }

    @Test
    void testInsertPartidoConFechaMenorALaActual() {
        Date ayer = Date.from(ZonedDateTime.now().minusDays(1).toInstant());
        PartidoDTO partido = new PartidoDTO(ayer, Estado.NO_INICIADO, jugadorDTO1, jugadorDTO2, canchaDTO1);
        assertThrows(IllegalArgumentException.class, () -> partidoService.save(partido));
        verify(partidoRepository,times(0)).save(any());
    }

    @Test
    void testUpdateExisting() {
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        when(partidoRepository.existsById(partidoParaAgregar.getId())).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoParaAgregar));
        when(partidoRepository.save(argumentCaptor.capture())).thenReturn(partidoParaAgregar);
        PartidoDTO partidoDTO = partidoService.update(partidoDTOParaAgregar);
        assertEquals(partidoParaAgregar.getId(), argumentCaptor.getValue().getId());
        assertEquals(partidoParaAgregar.getId(), partidoDTO.getId());
        assertEquals(partidoParaAgregar.getJugadorLocal().getId(), partidoDTO.getJugadorLocal().getId());
        assertEquals(partidoParaAgregar.getJugadorVisitante().getId(), partidoDTO.getJugadorVisitante().getId());
        assertEquals(partidoParaAgregar.getFechaComienzo(), partidoDTO.getFechaComienzo());
        verify(partidoRepository).existsById(eq(partidoParaAgregar.getId()));
        verify(partidoRepository).findById(eq(1L));
        verify(partidoRepository).save(any(Partido.class));

    }

    @Test
    void testUpdateNotFound() {
        when(partidoRepository.existsById(partidoParaAgregar.getId())).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> partidoService.update(partidoDTOParaAgregar));
        verify(partidoRepository).existsById(eq(partidoParaAgregar.getId()));
    }

    @Test
    void testUpdatePartidoConFechaMenorALaActual() {
        Date ayer = Date.from(ZonedDateTime.now().minusDays(1).toInstant());

        PartidoDTO partidoDTO = new PartidoDTO(ayer, Estado.NO_INICIADO, jugadorDTO1, jugadorDTO2, canchaDTO1);
        partidoDTO.setId(1L);

        Partido partido = this.mapper.fromDTO(partidoDTO);

        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        assertThrows(IllegalArgumentException.class, () -> partidoService.update(partidoDTO));
        verify(partidoRepository).existsById(eq(1L));
        verify(partidoRepository).findById(eq(1L));
    }

    @Test
    void testUpdatePartidoFinalizado() {
        PartidoDTO partidoFinalizado = new PartidoDTO(new Date(), Estado.FINALIZADO, jugadorDTO1, jugadorDTO2, canchaDTO1);
        partidoFinalizado.setId(1L);
        Partido partido = mapper.fromDTO(partidoFinalizado);

        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        assertThrows(IllegalArgumentException.class, () -> partidoService.update(partidoFinalizado));
        verify(partidoRepository).existsById(eq(1L));
        verify(partidoRepository).findById(eq(1L));
    }

    @Test
    void testUpdatePartidoEnCurso() {
        PartidoDTO partidoEnCurso = new PartidoDTO(new Date(), Estado.EN_CURSO, jugadorDTO1, jugadorDTO2, canchaDTO1);
        partidoEnCurso.setId(1L);

        Partido partido = mapper.fromDTO(partidoEnCurso);

        when(partidoRepository.existsById(1L)).thenReturn(true);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        assertThrows(IllegalArgumentException.class, () -> partidoService.update(partidoEnCurso));
        verify(partidoRepository).existsById(eq(1L));
        verify(partidoRepository).findById(eq(1L));
    }

    @Test
    void initGamePartidoInexistente() {
        when(partidoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> partidoService.initGame(1L));
        verify(partidoRepository).findById(eq(1L));
    }

    @Test
    void initGamePartidoExistente() {
        Partido partidoAIniciar = new Partido(new Date(), Estado.NO_INICIADO, jugador1, jugador1, cancha1);
        partidoAIniciar.setId(1L);
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoAIniciar));
        when(partidoRepository.save(argumentCaptor.capture())).thenReturn(new Partido());
        partidoService.initGame(1L);
        assertEquals(Estado.EN_CURSO, argumentCaptor.getValue().getEstado());
        verify(partidoRepository,times(2)).findById(eq(1L));
        verify(partidoRepository).save(any());
    }

    @Test
    void sumarPuntosPartidoInexistente() {
        when(partidoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> partidoService.sumarPuntos(1L, ModoJugador.LOCAL));
        verify(partidoRepository,times(1)).findById(eq(1L));
    }

    @Test
    void testSumarPuntosPartidoFinalizado() {
        Partido partidoFinalizado = new Partido(new Date(), Estado.FINALIZADO, jugador1, jugador1, cancha1);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoFinalizado));
        assertThrows(IllegalArgumentException.class, () -> partidoService.sumarPuntos(1L, ModoJugador.LOCAL));
        verify(partidoRepository,times(1)).findById(eq(1L));
    }

    @Test
    void testSumarPuntosPartidoNoInicializado() {
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoParaAgregar));
        assertThrows(IllegalArgumentException.class, () -> partidoService.sumarPuntos(1L, ModoJugador.LOCAL));
        verify(partidoRepository,times(1)).findById(eq(1L));
    }

    @Test
    void sumarPuntos1() {
        testSumarPuntos(SCORE_0, SCORE_15, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos2() {
        testSumarPuntos(SCORE_0, SCORE_30, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos3() {
        testSumarPuntos(SCORE_0, SCORE_40, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos4() {
        testSumarPuntosInconsistent(SCORE_0, SCORE_ADV, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos5() {
        testSumarPuntos(SCORE_15, SCORE_15, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos6() {
        testSumarPuntos(SCORE_15, SCORE_30, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos7() {
        testSumarPuntos(SCORE_15, SCORE_40, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos8() {
        testSumarPuntosInconsistent(SCORE_15, SCORE_ADV, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos9() {
        testSumarPuntos(SCORE_30, SCORE_15, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos10() {
        testSumarPuntos(SCORE_30, SCORE_30, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos11() {
        testSumarPuntos(SCORE_30, SCORE_40, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos12() {
        testSumarPuntosInconsistent(SCORE_30, SCORE_ADV, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos13() {
        testWinGamePoint(SCORE_40, SCORE_15, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos14() {
        testWinGamePoint(SCORE_40, SCORE_30, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos15() {
        testSumarPuntos(SCORE_40, SCORE_40, ModoJugador.LOCAL, false);
    }

    @Test
    void sumarPuntos16() {
        testSumarPuntos(SCORE_40, SCORE_ADV, ModoJugador.LOCAL, true);
    }

    @Test
    void sumarPuntos17() {
        testSumarPuntos(SCORE_0, SCORE_15, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos18() {
        testSumarPuntos(SCORE_0, SCORE_30, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos19() {
        testWinGamePoint(SCORE_0, SCORE_40, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos20() {
        testSumarPuntosInconsistent(SCORE_0, SCORE_ADV, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos21() {
        testSumarPuntos(SCORE_15, SCORE_15, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos22() {
        testSumarPuntos(SCORE_15, SCORE_30, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos23() {
        testWinGamePoint(SCORE_15, SCORE_40, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos24() {
        testSumarPuntosInconsistent(SCORE_15, SCORE_ADV, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos25() {
        testSumarPuntos(SCORE_30, SCORE_15, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos26() {
        testSumarPuntos(SCORE_30, SCORE_30, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos27() {
        testWinGamePoint(SCORE_30, SCORE_40, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos28() {
        testSumarPuntosInconsistent(SCORE_30, SCORE_ADV, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos29() {
        testSumarPuntos(SCORE_40, SCORE_15, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos30() {
        testSumarPuntos(SCORE_40, SCORE_30, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos31() {
        testSumarPuntos(SCORE_40, SCORE_40, ModoJugador.VISITANTE, false);
    }

    @Test
    void sumarPuntos32() {
        testWinGamePoint(SCORE_40, SCORE_ADV, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos33() {
        testWinMatchPoint(SCORE_15, SCORE_40, ModoJugador.VISITANTE);
    }

    @Test
    void sumarPuntos34() {
        testWinMatchPoint(SCORE_40, SCORE_15, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos35() {
        testSumarPuntos(SCORE_ADV, SCORE_40, ModoJugador.VISITANTE, true);
    }

    @Test
    void sumarPuntos36() {
        testSumarPuntosInconsistent(SCORE_ADV, SCORE_15, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos37() {
        testSumarPuntosInconsistent(SCORE_ADV, SCORE_30, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos38() {
        testWinGamePoint(SCORE_ADV, SCORE_40, ModoJugador.LOCAL);
    }

    @Test
    void sumarPuntos39() {
        testSumarPuntosInconsistent(SCORE_ADV, SCORE_0, ModoJugador.VISITANTE);
    }

    private void testSumarPuntos(int localScore, int visitanteScore, ModoJugador modoJugador, boolean deuce) {
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);

        Partido partido = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);
        Partido partidoClon = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);

        when(partidoRepository.save(argumentCaptor.capture())).thenAnswer((Answer<Partido>) invocation -> (Partido) invocation.getArguments()[0]);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));
        PartidoDTO partidoResult = partidoService.sumarPuntos(1L, modoJugador);
        assertEquals(partidoResult.getId(), argumentCaptor.getValue().getId());
        assertEquals(argumentCaptor.getValue().getId(), partidoClon.getId());
        assertEquals(argumentCaptor.getValue().getCancha(), partidoClon.getCancha());
        assertEquals(argumentCaptor.getValue().getCantidadGamesLocal(), partidoClon.getCantidadGamesLocal());
        assertEquals(argumentCaptor.getValue().getCantidadGamesVisitante(), partidoClon.getCantidadGamesVisitante());
        assertEquals(argumentCaptor.getValue().getJugadorLocal(), partidoClon.getJugadorLocal());
        assertEquals(argumentCaptor.getValue().getJugadorVisitante(), partidoClon.getJugadorVisitante());
        assertEquals(argumentCaptor.getValue().getEstado(), partidoClon.getEstado());

        if (modoJugador.equals(ModoJugador.LOCAL)) {
            if (deuce) {
                assertEquals(argumentCaptor.getValue().getScoreLocal(), localScore);
                assertEquals(argumentCaptor.getValue().getScoreVisitante(), visitanteScore - 1);
            } else {
                assertEquals(argumentCaptor.getValue().getScoreLocal(), localScore + 1);
                assertEquals(argumentCaptor.getValue().getScoreVisitante(), visitanteScore);
            }
        } else {
            if (deuce) {
                assertEquals(argumentCaptor.getValue().getScoreVisitante(), visitanteScore);
                assertEquals(argumentCaptor.getValue().getScoreLocal(), localScore - 1);
            } else {
                assertEquals(argumentCaptor.getValue().getScoreVisitante(), visitanteScore + 1);
                assertEquals(argumentCaptor.getValue().getScoreLocal(), localScore);
            }
        }
        verify(partidoRepository).save(any());
        verify(partidoRepository).findById(eq(1L));

    }

    private void testWinGamePoint(int localScore, int visitanteScore, ModoJugador modoJugador) {
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        Partido partido = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);
        Partido partidoClon = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);

        when(partidoRepository.save(argumentCaptor.capture())).thenAnswer((Answer<Partido>) invocation -> (Partido) invocation.getArguments()[0]);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        PartidoDTO partidoResult = partidoService.sumarPuntos(1L, modoJugador);

        assertEquals(partidoResult.getId(), argumentCaptor.getValue().getId());
        assertEquals(argumentCaptor.getValue().getId(), partidoClon.getId());
        assertEquals(argumentCaptor.getValue().getCancha(), partidoClon.getCancha());
        if (modoJugador.equals(ModoJugador.LOCAL)) {
            assertEquals(argumentCaptor.getValue().getCantidadGamesLocal(), partidoClon.getCantidadGamesLocal() + 1);
            assertEquals(argumentCaptor.getValue().getCantidadGamesVisitante(), partidoClon.getCantidadGamesVisitante());
        } else {
            assertEquals(argumentCaptor.getValue().getCantidadGamesLocal(), partidoClon.getCantidadGamesLocal());
            assertEquals(argumentCaptor.getValue().getCantidadGamesVisitante(), partidoClon.getCantidadGamesVisitante() + 1);
        }
        assertEquals(argumentCaptor.getValue().getJugadorLocal(), partidoClon.getJugadorLocal());
        assertEquals(argumentCaptor.getValue().getJugadorVisitante(), partidoClon.getJugadorVisitante());
        assertEquals(0, argumentCaptor.getValue().getScoreVisitante());
        assertEquals(0, argumentCaptor.getValue().getScoreLocal());
        assertEquals(argumentCaptor.getValue().getEstado(), partidoClon.getEstado());

        verify(partidoRepository).save(any());
        verify(partidoRepository).findById(eq(1L));
    }

    private void testWinMatchPoint(int localScore, int visitanteScore, ModoJugador modoJugador) {
        ArgumentCaptor<Partido> argumentCaptor = ArgumentCaptor.forClass(Partido.class);
        Partido partido = new Partido(1L, new Date(), Estado.EN_CURSO, jugador1, jugador1, localScore, 0, visitanteScore, 0, cancha1);

        if (modoJugador.equals(ModoJugador.LOCAL)) {
            partido.setCantidadGamesLocal(5);
            partido.setCantidadGamesVisitante(1);
        }
        if (modoJugador.equals(ModoJugador.VISITANTE)) {
            partido.setCantidadGamesLocal(1);
            partido.setCantidadGamesVisitante(5);
        }
        when(partidoRepository.save(argumentCaptor.capture())).thenAnswer((Answer<Partido>) invocation -> (Partido) invocation.getArguments()[0]);
        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partido));

        PartidoDTO partidoResult = partidoService.sumarPuntos(1L, modoJugador);

        assertEquals(partidoResult.getId(), argumentCaptor.getValue().getId());
        assertEquals(0, argumentCaptor.getValue().getScoreVisitante());
        assertEquals(0, argumentCaptor.getValue().getScoreLocal());
        assertEquals(Estado.FINALIZADO, argumentCaptor.getValue().getEstado());
        verify(partidoRepository).save(any());
        verify(partidoRepository).findById(eq(1L));
    }

    private void testSumarPuntosInconsistent(int localScore, int visitanteScore, ModoJugador modoJugador) {
        Partido partidoAIniciar = new Partido(new Date(), Estado.NO_INICIADO, jugador1, jugador1, cancha1);
        partidoAIniciar.setId(1L);
        partidoAIniciar.setScoreLocal(localScore);
        partidoAIniciar.setScoreVisitante(visitanteScore);

        when(partidoRepository.findById(1L)).thenReturn(Optional.of(partidoAIniciar));

        assertThrows(IllegalArgumentException.class, () -> partidoService.sumarPuntos(1L, modoJugador));

        verify(partidoRepository).findById(eq(1L));

    }
}

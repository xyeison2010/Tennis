package com.baufest.tennis.springtennis.controller;


import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.leo.tennis.controller.JugadorController;
import com.leo.tennis.dto.JugadorDTO;
import com.leo.tennis.service.JugadorServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(JugadorController.class)
class JugadorControllerTest {
    String basePath = "/springtennis/api/v1/jugadores/";

    List<JugadorDTO> jugadoresDePrueba = new ArrayList<>();
    JSONArray jugadoresDePruebaEnJSON = new JSONArray();
    JugadorDTO jugadorParaAgregar = new JugadorDTO();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JugadorController jugadorController;

    @MockBean
    JugadorServiceImpl jugadorService;

    @BeforeEach
    public void setUp() {
        jugadoresDePrueba.add(new JugadorDTO());
        jugadoresDePrueba.add(new JugadorDTO());
        jugadoresDePrueba.add(new JugadorDTO());
        jugadoresDePrueba.add(new JugadorDTO());
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

        jugadorParaAgregar.setId(5L);
        jugadorParaAgregar.setNombre("lucas");
        jugadorParaAgregar.setPuntos(25);

        jugadoresDePrueba.forEach((x) -> jugadoresDePruebaEnJSON.put(x.toJSONObject()));

    }

    @Test
    void testListAll() throws Exception {

        when(jugadorService.listAll()).thenReturn(jugadoresDePrueba);

        mockMvc.perform(MockMvcRequestBuilders.get(basePath).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jugadoresDePruebaEnJSON.toString()));

        verify(jugadorService).listAll();

    }

    @Test
    void testGetByID() throws Exception {
        long idJugadorGet = 1L;
        when(jugadorService.getById(1L)).thenReturn(jugadoresDePrueba.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + idJugadorGet).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jugadoresDePrueba.get(0)
                        .toJSONObject().toString()));
        verify(jugadorService).getById(1L);
    }

    @Test
    void testSaveJugador() throws Exception {
        ArgumentCaptor<JugadorDTO> argumentCaptor = ArgumentCaptor.forClass(JugadorDTO.class);

        when(jugadorService.save(argumentCaptor.capture())).thenReturn(new JugadorDTO());

        mockMvc.perform(MockMvcRequestBuilders.post(basePath).contentType(MediaType.APPLICATION_JSON)
                .content(jugadorParaAgregar.toJSONObject().toString()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertEquals(jugadorParaAgregar.getNombre(),argumentCaptor.getValue().getNombre());
        assertEquals(jugadorParaAgregar.getId(),argumentCaptor.getValue().getId());
        assertEquals(jugadorParaAgregar.getPuntos(),argumentCaptor.getValue().getPuntos());
        verify(jugadorService).save(any(JugadorDTO.class));
    }

    @Test
    void testUpdateJugador() throws Exception {
        ArgumentCaptor<JugadorDTO> argumentCaptor = ArgumentCaptor.forClass(JugadorDTO.class);
        when(jugadorService.update(argumentCaptor.capture())).thenReturn(new JugadorDTO());

        mockMvc.perform(MockMvcRequestBuilders.put(basePath + jugadoresDePrueba.get(0).getId()).contentType(MediaType.APPLICATION_JSON)
                .content(jugadoresDePrueba.get(0).toJSONObject().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals(jugadoresDePrueba.get(0).getId(),argumentCaptor.getValue().getId());
        assertEquals(jugadoresDePrueba.get(0).getNombre(),argumentCaptor.getValue().getNombre());
        assertEquals(jugadoresDePrueba.get(0).getPuntos(),argumentCaptor.getValue().getPuntos());
        verify(jugadorService).update(any(JugadorDTO.class));
    }

    @Test
    void testDeleteJugador() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(basePath + jugadoresDePrueba.get(0).getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(jugadorService).delete(eq(jugadoresDePrueba.get(0).getId()));
    }

    @Test
    void testRecalculateRanking() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + jugadoresDePrueba.get(0).getId()+
                "/actions/recalculateRanking"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(jugadorService).recalculateRanking(argumentCaptor.capture());
        assertEquals(jugadoresDePrueba.get(0).getId(),argumentCaptor.getValue());
    }
}

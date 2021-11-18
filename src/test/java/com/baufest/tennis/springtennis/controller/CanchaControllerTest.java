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

import com.leo.tennis.controller.CanchaController;
import com.leo.tennis.dto.CanchaDTO;
import com.leo.tennis.service.CanchaServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(CanchaController.class)
class CanchaControllerTest {
	String basePath = "/springtennis/api/v1/canchas/";
	List<CanchaDTO> canchasDePrueba = new ArrayList<>();
	JSONArray canchasDePruebaEnJSON = new JSONArray();
	CanchaDTO canchaParaAgregar = new CanchaDTO();

	@Autowired
	MockMvc mockMvc;

	@Autowired
	CanchaController canchaController;

	@MockBean
	CanchaServiceImpl canchaService;

	@BeforeEach
	public void setUp() {
		canchasDePrueba.clear();
		canchasDePrueba.add(new CanchaDTO());
		canchasDePrueba.add(new CanchaDTO());
		canchasDePrueba.add(new CanchaDTO());
		canchasDePrueba.add(new CanchaDTO());

		canchasDePrueba.get(0).setNombre("cancha 1");
		canchasDePrueba.get(1).setNombre("cancha 2");
		canchasDePrueba.get(2).setNombre("cancha 3");
		canchasDePrueba.get(3).setNombre("cancha 4");
		canchasDePrueba.get(0).setId(1L);
		canchasDePrueba.get(1).setId(2L);
		canchasDePrueba.get(2).setId(3L);
		canchasDePrueba.get(3).setId(4L);
		canchasDePrueba.get(0).setDireccion("av random 123");
		canchasDePrueba.get(1).setDireccion("calle rgn 321");
		canchasDePrueba.get(2).setDireccion("calle inventada 777");
		canchasDePrueba.get(3).setDireccion("av siempreviva 156");

		canchaParaAgregar.setId(5L);
		canchaParaAgregar.setNombre("cancha 5");
		canchaParaAgregar.setDireccion("calle sin nombre magica");

		canchasDePrueba.forEach((x) -> canchasDePruebaEnJSON.put(x.toJSONObject()));

	}

	@Test
	void testListAll() throws Exception {
		when(canchaService.listAll()).thenReturn(canchasDePrueba);

		mockMvc.perform(MockMvcRequestBuilders.get(basePath).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(canchasDePruebaEnJSON.toString()));

		verify(canchaService).listAll();

	}

	@Test
	void testGetByID() throws Exception {
		long idCanchaGet = 1L;
		when(canchaService.getById(1L)).thenReturn(canchasDePrueba.get(0));

		mockMvc.perform(MockMvcRequestBuilders.get(basePath + idCanchaGet).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(canchasDePrueba.get(0)
						.toJSONObject().toString()));

		verify(canchaService).getById(eq(1L));

	}

	@Test
	void testSaveCancha() throws Exception {
		ArgumentCaptor<CanchaDTO> argumentCaptor = ArgumentCaptor.forClass(CanchaDTO.class);

		when(canchaService.save(argumentCaptor.capture())).thenReturn(new CanchaDTO());

		mockMvc.perform(MockMvcRequestBuilders.post(basePath).contentType(MediaType.APPLICATION_JSON)
				.content(canchaParaAgregar.toJSONObject().toString()))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		assertEquals(canchaParaAgregar.getNombre(), argumentCaptor.getValue().getNombre());
		verify(canchaService).save(any(CanchaDTO.class));
	}


	@Test
	void testUpdateCancha() throws Exception {
		ArgumentCaptor<CanchaDTO> argumentCaptor = ArgumentCaptor.forClass(CanchaDTO.class);
		when(canchaService.update(argumentCaptor.capture())).thenReturn(new CanchaDTO());

		mockMvc.perform(MockMvcRequestBuilders.put(basePath+ canchasDePrueba.get(0).getId()).contentType(MediaType.APPLICATION_JSON)
				.content(canchaParaAgregar.toJSONObject().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		assertEquals(canchasDePrueba.get(0).getId(), argumentCaptor.getValue().getId());
		assertEquals(canchaParaAgregar.getNombre(), argumentCaptor.getValue().getNombre());
		verify(canchaService).update(any(CanchaDTO.class));
	}

	@Test
	void testDeleteCancha() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(basePath+ canchasDePrueba.get(0).getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		verify(canchaService).delete(eq(canchasDePrueba.get(0).getId()));
	}
}

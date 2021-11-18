package com.leo.tennis.dto;

import org.json.JSONObject;

import javax.persistence.*;

/**
 * <p>
 * Data transfer object de Jugador (Clase)
 * </p>
 * clase utilizada como DTO de jugador
 */

//solo se lleva su getter y setter ,constructor vacio y lleno nada mas
public class JugadorDTO {

	private Long id;

	private String nombre;

	private int puntos;

	private EntrenadorDTO entrenador;

	private int ganancia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public EntrenadorDTO getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(EntrenadorDTO entrenador) {
		this.entrenador = entrenador;
	}

	public int getGanancia() {
		return ganancia;
	}

	public void setGanancia(int ganancia) {
		this.ganancia = ganancia;
	}

	public JugadorDTO() {
		super();
	}

	public JugadorDTO(Long id, String nombre, int puntos, EntrenadorDTO entrenador, int ganancia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.puntos = puntos;
		this.entrenador = entrenador;
		this.ganancia = ganancia;
	}

	/* Metodo para retornar nuestro objeto en un formato JSON */
	/*
	 * Este metodo es muy utilizado para poder transformar el objeto a JSON en caso
	 * de ser necesario para retorno
	 */
	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("nombre", getNombre());
		jo.put("puntos", getPuntos());
		return jo;
	}

}
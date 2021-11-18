package com.leo.tennis.dto;

import org.json.JSONObject;

public class CanchaDTO {
	
	
	private Long id;

	private String nombre;

	private String direccion;

	
	//
	public CanchaDTO(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public CanchaDTO() {
	}

	//
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
//
	@Override
	public String toString() {
		return "Cancha [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + "]";
	}
	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id",getId());
		jo.put("nombre",getNombre());
		jo.put("direccion",getDireccion());
		return jo;
	}
}

package com.leo.tennis.dto;


public class EntrenadorDTO {

	private Long id;

	private String nombre;

	public EntrenadorDTO(Long id, String nombre) {
		//super();
		this.id = id;
		this.nombre = nombre;
	}

	public EntrenadorDTO() {
		super();
	}

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
	


}

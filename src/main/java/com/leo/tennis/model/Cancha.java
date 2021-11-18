package com.leo.tennis.model;

import org.json.JSONObject;
import javax.persistence.*;

@Entity
@Table(name = "Cancha")
public class Cancha {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private String direccion;

	public Cancha(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public Cancha() {
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

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

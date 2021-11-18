package com.leo.tennis.model;

import org.json.JSONObject;



import javax.persistence.*;

/**
 * <p>
 * Entidad Jugador
 * </p>
 * Esta entidad sera la que mapee el JPA para transformarlo en una tabla con sus
 * respectivas columnas.
 */
@Entity
@Table(name = "Jugador")
public class Jugador {
//YA SE CUAL ES EL ERROR DEBE ESTAR AKI ENTRENADOR JOIN COLUM UN 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private int puntos;
//para manytoone,debeser,mejor,lazy
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEntrenador")
	private Entrenador entrenador;
    
	@Column
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

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	public int getGanancia() {
		return ganancia;
	}

	public void setGanancia(int ganancia) {
		this.ganancia = ganancia;
	}

	public Jugador(Long id, String nombre, int puntos, Entrenador entrenador, int ganancia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.puntos = puntos;
		this.entrenador = entrenador;
		this.ganancia = ganancia;
	}

	public Jugador() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entrenador == null) ? 0 : entrenador.hashCode());
		result = prime * result + ganancia;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + puntos;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		if (entrenador == null) {
			if (other.entrenador != null)
				return false;
		} else if (!entrenador.equals(other.entrenador))
			return false;
		if (ganancia != other.ganancia)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (puntos != other.puntos)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nombre=" + nombre + ", puntos=" + puntos + ", entrenador=" + entrenador
				+ ", ganancia=" + ganancia + "]";
	}


	




}

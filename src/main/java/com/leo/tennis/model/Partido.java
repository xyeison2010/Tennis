package com.leo.tennis.model;


import org.json.JSONObject;

import com.leo.tennis.enums.Estado;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "partido")
public class Partido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date fechaComienzo;

	@Column(nullable = false)
	private Estado estado;

	// En los ManyToOne, OneToOne, OneToMany se producen las relaciones entre las

	// que varios partidos pueden pertenecerle a un jugador_local
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idLocal", nullable = false)
	private Jugador jugadorLocal;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idVisitante", nullable = false)
	private Jugador jugadorVisitante;

	@Column(nullable = true)
	private int scoreLocal;

	@Column(nullable = true)
	private String puntosGameActualLocal;

	@Column(nullable = true)
	private int cantidadGamesLocal;

	@Column(nullable = true)
	private int scoreVisitante;

	@Column(nullable = true)
	private String puntosGameActualVisitante;

	@Column(nullable = true)
	private int cantidadGamesVisitante;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCancha", nullable = false)
	private Cancha cancha;

	public Partido() {
	}

	public Partido(Date fechaComienzo, Estado estado, Jugador jugadorLocal, Jugador jugadorVisitante, Cancha cancha) {
		this.fechaComienzo = fechaComienzo;
		this.estado = estado;
		this.jugadorLocal = jugadorLocal;
		this.jugadorVisitante = jugadorVisitante;
		this.scoreLocal = 0;
		this.puntosGameActualLocal = "0";
		this.cantidadGamesLocal = 0;
		this.scoreVisitante = 0;
		this.puntosGameActualVisitante = "0";
		this.cantidadGamesVisitante = 0;
		this.cancha = cancha;
	}

	public Partido(Long id, Date fechaComienzo, Estado estado, Jugador jugadorLocal, Jugador jugadorVisitante,
			int scoreLocal, int cantidadGamesLocal, int scoreVisitante, int cantidadGamesVisitante, Cancha cancha) {
		this.id = id;
		this.fechaComienzo = fechaComienzo;
		this.estado = estado;
		this.jugadorLocal = jugadorLocal;
		this.jugadorVisitante = jugadorVisitante;
		this.scoreLocal = scoreLocal;
		this.cantidadGamesLocal = cantidadGamesLocal;
		this.scoreVisitante = scoreVisitante;
		this.cantidadGamesVisitante = cantidadGamesVisitante;
		this.cancha = cancha;
	}

	public Partido(Date fechaComienzo, Estado estado, Jugador jugadorLocal, Jugador jugadorVisitante, int scoreLocal,
			String puntosGameActualLocal, int cantidadGamesLocal, int scoreVisitante, String puntosGameActualVisitante,
			int cantidadGamesVisitante, Cancha cancha) {
		this.fechaComienzo = fechaComienzo;
		this.estado = estado;
		this.jugadorLocal = jugadorLocal;
		this.jugadorVisitante = jugadorVisitante;
		this.scoreLocal = scoreLocal;
		this.puntosGameActualLocal = puntosGameActualLocal;
		this.cantidadGamesLocal = cantidadGamesLocal;
		this.scoreVisitante = scoreVisitante;
		this.puntosGameActualVisitante = puntosGameActualVisitante;
		this.cantidadGamesVisitante = cantidadGamesVisitante;
		this.cancha = cancha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaComienzo() {
		return fechaComienzo;
	}

	public void setFechaComienzo(Date fechaComienzo) {
		this.fechaComienzo = fechaComienzo;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Jugador getJugadorLocal() {
		return jugadorLocal;
	}

	public void setJugadorLocal(Jugador jugadorLocal) {
		this.jugadorLocal = jugadorLocal;
	}

	public Jugador getJugadorVisitante() {
		return jugadorVisitante;
	}

	public void setJugadorVisitante(Jugador jugadorVisitante) {
		this.jugadorVisitante = jugadorVisitante;
	}

	public int getScoreLocal() {
		return scoreLocal;
	}

	public void setScoreLocal(int scoreLocal) {
		this.scoreLocal = scoreLocal;
	}

	public String getPuntosGameActualLocal() {
		return puntosGameActualLocal;
	}

	public void setPuntosGameActualLocal(String puntosGameActualLocal) {
		this.puntosGameActualLocal = puntosGameActualLocal;
	}

	public int getCantidadGamesLocal() {
		return cantidadGamesLocal;
	}

	public void setCantidadGamesLocal(int cantidadGamesLocal) {
		this.cantidadGamesLocal = cantidadGamesLocal;
	}

	public int getScoreVisitante() {
		return scoreVisitante;
	}

	public void setScoreVisitante(int scoreVisitante) {
		this.scoreVisitante = scoreVisitante;
	}

	public String getPuntosGameActualVisitante() {
		return puntosGameActualVisitante;
	}

	public void setPuntosGameActualVisitante(String puntosGameActualVisitante) {
		this.puntosGameActualVisitante = puntosGameActualVisitante;
	}

	public int getCantidadGamesVisitante() {
		return cantidadGamesVisitante;
	}

	public void setCantidadGamesVisitante(int cantidadGamesVisitante) {
		this.cantidadGamesVisitante = cantidadGamesVisitante;
	}

	public Cancha getCancha() {
		return cancha;
	}

	public void setCancha(Cancha cancha) {
		this.cancha = cancha;
	}

	/* Metodo para retornar nuestro modelo de datos en formato de String */
	@Override
	public String toString() {
		return "partido [id=" + id + ", fechaComienzo=" + fechaComienzo + ", estado=" + estado + ", jugadorLocal="
				+ jugadorLocal + ", jugadorVisitante=" + jugadorVisitante + ", scoreLocal=" + scoreLocal
				+ ", puntosGameActualLocal=" + puntosGameActualLocal + ", cantidadGamesLocal=" + cantidadGamesLocal
				+ ", scoreVisitante=" + scoreVisitante + ", puntosGameActualVisitante=" + puntosGameActualVisitante
				+ ", cantidadGamesVisitante=" + cantidadGamesVisitante + "]";
	}

	/* Metodo para retornar nuestro modelo de datos en un formato JSON */
	/* Metodo para retornar nuestro objeto en un formato JSON */
	/*
	 * Este metodo es muy utilizado para poder transformar el objeto a JSON en caso
	 * de ser necesario para retorno
	 */
	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("fechaComienzo", getFechaComienzo());
		jo.put("estado", getEstado());
		jo.put("jugadorLocal", getJugadorLocal());
		jo.put("jugadorVisitante", getJugadorVisitante());
		jo.put("scoreLocal", getScoreLocal());
		jo.put("puntosGameActualLocal", getPuntosGameActualLocal());
		jo.put("cantidadGamesLocal", getCantidadGamesLocal());
		jo.put("scoreVisitante", getScoreVisitante());
		jo.put("puntosGameActualVisitante", getPuntosGameActualVisitante());
		jo.put("cantidadGamesVisitante", getCantidadGamesVisitante());
		jo.put("cancha", getCancha().toJSONObject());
		return jo;
	}
}

package com.leo.tennis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.leo.tennis.enums.Estado;



@Entity
public class Torneo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// que varios partidos pueden pertenecerle a un jugador_local
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( nullable = false)
	private Jugador jugador1;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private Jugador jugador2;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( nullable = false)
	private Jugador jugador3;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( nullable = false)
	private Jugador jugador4;	
//
	@Column(nullable = false)
	private Date fechaComienzo;

	@Column(nullable = false)
	private Estado estado;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn( nullable = false)
	private Cancha cancha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public void setJugador1(Jugador jugador1) {
		this.jugador1 = jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}

	public void setJugador2(Jugador jugador2) {
		this.jugador2 = jugador2;
	}

	public Jugador getJugador3() {
		return jugador3;
	}

	public void setJugador3(Jugador jugador3) {
		this.jugador3 = jugador3;
	}

	public Jugador getJugador4() {
		return jugador4;
	}

	public void setJugador4(Jugador jugador4) {
		this.jugador4 = jugador4;
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

	public Cancha getCancha() {
		return cancha;
	}

	public void setCancha(Cancha cancha) {
		this.cancha = cancha;
	}

	public Torneo(Long id, Jugador jugador1, Jugador jugador2, Jugador jugador3, Jugador jugador4, Date fechaComienzo,
			Estado estado, Cancha cancha) {
		super();
		this.id = id;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.jugador3 = jugador3;
		this.jugador4 = jugador4;
		this.fechaComienzo = fechaComienzo;
		this.estado = estado;
		this.cancha = cancha;
	}

	public Torneo() {
		super();
	}

	@Override
	public String toString() {
		return "Torneo [id=" + id + ", jugador1=" + jugador1 + ", jugador2=" + jugador2 + ", jugador3=" + jugador3
				+ ", jugador4=" + jugador4 + ", fechaComienzo=" + fechaComienzo + ", estado=" + estado + ", cancha="
				+ cancha + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cancha == null) ? 0 : cancha.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((fechaComienzo == null) ? 0 : fechaComienzo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jugador1 == null) ? 0 : jugador1.hashCode());
		result = prime * result + ((jugador2 == null) ? 0 : jugador2.hashCode());
		result = prime * result + ((jugador3 == null) ? 0 : jugador3.hashCode());
		result = prime * result + ((jugador4 == null) ? 0 : jugador4.hashCode());
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
		Torneo other = (Torneo) obj;
		if (cancha == null) {
			if (other.cancha != null)
				return false;
		} else if (!cancha.equals(other.cancha))
			return false;
		if (estado != other.estado)
			return false;
		if (fechaComienzo == null) {
			if (other.fechaComienzo != null)
				return false;
		} else if (!fechaComienzo.equals(other.fechaComienzo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jugador1 == null) {
			if (other.jugador1 != null)
				return false;
		} else if (!jugador1.equals(other.jugador1))
			return false;
		if (jugador2 == null) {
			if (other.jugador2 != null)
				return false;
		} else if (!jugador2.equals(other.jugador2))
			return false;
		if (jugador3 == null) {
			if (other.jugador3 != null)
				return false;
		} else if (!jugador3.equals(other.jugador3))
			return false;
		if (jugador4 == null) {
			if (other.jugador4 != null)
				return false;
		} else if (!jugador4.equals(other.jugador4))
			return false;
		return true;
	}

	
	
}


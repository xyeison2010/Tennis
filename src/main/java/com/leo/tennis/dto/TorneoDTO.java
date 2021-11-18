package com.leo.tennis.dto;

import java.util.Date;

import com.leo.tennis.enums.Estado;



public class TorneoDTO {

private Long id;

private JugadorDTO jugador1;

private JugadorDTO jugador2;

private JugadorDTO jugador3;

private JugadorDTO jugador4;



private Date fechaComienzo;

private Estado estado;

private CanchaDTO cancha;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public JugadorDTO getJugador1() {
	return jugador1;
}

public void setJugador1(JugadorDTO jugador1) {
	this.jugador1 = jugador1;
}

public JugadorDTO getJugador2() {
	return jugador2;
}

public void setJugador2(JugadorDTO jugador2) {
	this.jugador2 = jugador2;
}

public JugadorDTO getJugador3() {
	return jugador3;
}

public void setJugador3(JugadorDTO jugador3) {
	this.jugador3 = jugador3;
}

public JugadorDTO getJugador4() {
	return jugador4;
}

public void setJugador4(JugadorDTO jugador4) {
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

public CanchaDTO getCancha() {
	return cancha;
}

public void setCancha(CanchaDTO cancha) {
	this.cancha = cancha;
}

public TorneoDTO(Long id, JugadorDTO jugador1, JugadorDTO jugador2, JugadorDTO jugador3, JugadorDTO jugador4,
		Date fechaComienzo, Estado estado, CanchaDTO cancha) {
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

public TorneoDTO() {
	super();
}

@Override
public String toString() {
	return "TorneoDTO [id=" + id + ", jugador1=" + jugador1 + ", jugador2=" + jugador2 + ", jugador3=" + jugador3
			+ ", jugador4=" + jugador4 + ", fechaComienzo=" + fechaComienzo + ", estado=" + estado + ", cancha="
			+ cancha + "]";
}




}

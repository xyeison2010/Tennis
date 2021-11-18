package com.leo.tennis.dto;

import org.json.JSONObject;

import com.leo.tennis.enums.Estado;

import java.util.Date;

/**
 * <p>Data transfer object de Partido (Clase)</p>
 * clase utilizada como DTO de Partido
 */
public class PartidoDTO {

	/* PartidoDTO tiene la misma estructura de nuestra entidad partido, esto se debe
	 * a que es un DTO = Data Transfer Object, estos DTO se utilizan para poder modificar los valores
	 * de los DTO, filtrarlos, etc, sin persistir en base de datos, tambien es utilizado para poder devolver
	 * una entidad transformada como response ignorando atributos sensibles, como por EJ una contrase;a,
	 */

	/*Atributos privados de la clase*/

	private Long id;

	private Date fechaComienzo;

	private Estado estado;

	private JugadorDTO jugadorLocal;//

	private JugadorDTO jugadorVisitante;//

	private int scoreLocal;

	private String puntosGameActualLocal;

	private int cantidadGamesLocal;

	private int scoreVisitante;

	private String puntosGameActualVisitante;

	private int cantidadGamesVisitante;
	
	//en un dto asi se hace la instancia de entityxd
	private CanchaDTO cancha;//

	/* Los constructores se utilizan al momento de instanciar nuesta clase y darle espacio en memoria,
	 * los atributos de nuesta clase que no contengan instanciacion en el constructor quedaran con valor null
	 * los constructores pueden ser overraideados y contener instanciaciones para varios atributos distintos o
	 * incluso el constructor vacio*/

	public PartidoDTO() {
	}

	public PartidoDTO(Date fechaComienzo, Estado estado, JugadorDTO jugadorLocal, JugadorDTO jugadorVisitante, CanchaDTO cancha) {
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

	public PartidoDTO(Long id, Date fechaComienzo, Estado estado, JugadorDTO jugadorLocal, JugadorDTO jugadorVisitante, int scoreLocal, int cantidadGamesLocal, int scoreVisitante, int cantidadGamesVisitante, CanchaDTO cancha) {
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

	public PartidoDTO(Date fechaComienzo, Estado estado, JugadorDTO jugadorLocal, JugadorDTO jugadorVisitante, int scoreLocal,
					  String puntosGameActualLocal, int cantidadGamesLocal, int scoreVisitante, String puntosGameActualVisitante,
					  int cantidadGamesVisitante, CanchaDTO cancha) {
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

	/* Getters & Setters */

	/* Los getters y setters se utilizan para acceder a los atributos de nuestro objeto,
	como estos son PRIVADOS solo pueden ser accedidos desde metodos publicos, los cuales llamamos
	getters y setters, estos permiten modificar o obtener los atributos privados de la clase,
	si queremos que un atributo no sea accesible para cambio ni obtenerlo simplemente borramos el getter
	y el setter, de forma natural no habria forma de acceder a dicho atributo fuera de la instanciacion
	 */

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

	public JugadorDTO getJugadorLocal() {
		return jugadorLocal;
	}

	public void setJugadorLocal(JugadorDTO jugadorLocal) {
		this.jugadorLocal = jugadorLocal;
	}

	public JugadorDTO getJugadorVisitante() {
		return jugadorVisitante;
	}

	public void setJugadorVisitante(JugadorDTO jugadorVisitante) {
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

	public CanchaDTO getCancha() {
		return cancha;
	}

	public void setCancha(CanchaDTO cancha) {
		this.cancha = cancha;
	}


	@Override
	public String toString() {
		return "partido [id=" + id + ", fechaComienzo=" + fechaComienzo + ", estado=" + estado + ", jugadorLocal="
				+ jugadorLocal + ", jugadorVisitante=" + jugadorVisitante + ", scoreLocal=" + scoreLocal
				+ ", puntosGameActualLocal=" + puntosGameActualLocal + ", cantidadGamesLocal=" + cantidadGamesLocal
				+ ", scoreVisitante=" + scoreVisitante + ", puntosGameActualVisitante=" + puntosGameActualVisitante
				+ ", cantidadGamesVisitante=" + cantidadGamesVisitante + "]";
	}

	/* Metodo para retornar nuestro objeto en un formato JSON */
	/*Este metodo es muy utilizado para poder transformar el objeto a JSON en caso de ser necesario para retorno*/

	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("id", getId());
		jo.put("fechaComienzo", getFechaComienzo());
		jo.put("estado", getEstado());
		jo.put("jugadorLocal",getJugadorLocal());
		jo.put("jugadorVisitante",getJugadorVisitante());
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

package co.edu.unal.sweng1.calzado.negocio;

import java.util.List;

public class DeclaracionNoValidableException extends RuntimeException {

	private final List<CampoDeclaracion> camposPendientes;

	public DeclaracionNoValidableException(List<CampoDeclaracion> camposPendientes) {
		super("La declaración tiene campos pendientes de revisión.");
		this.camposPendientes = List.copyOf(camposPendientes);
	}

	public List<CampoDeclaracion> getCamposPendientes() {
		return camposPendientes;
	}
}

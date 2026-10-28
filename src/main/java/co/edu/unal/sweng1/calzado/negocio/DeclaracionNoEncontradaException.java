package co.edu.unal.sweng1.calzado.negocio;

public class DeclaracionNoEncontradaException extends RuntimeException {

	public DeclaracionNoEncontradaException(Long id) {
		super("No existe la declaración con id " + id + ".");
	}
}

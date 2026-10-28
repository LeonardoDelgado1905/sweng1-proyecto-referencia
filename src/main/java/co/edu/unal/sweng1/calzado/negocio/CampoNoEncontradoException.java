package co.edu.unal.sweng1.calzado.negocio;

public class CampoNoEncontradoException extends RuntimeException {

	public CampoNoEncontradoException(Long id) {
		super("No existe el campo con id " + id + " en la declaración.");
	}
}

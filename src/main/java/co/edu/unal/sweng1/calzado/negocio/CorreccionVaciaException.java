package co.edu.unal.sweng1.calzado.negocio;

public class CorreccionVaciaException extends RuntimeException {
    public CorreccionVaciaException() {
        super("La corrección no puede estar vacía.");
    }
    
}

package co.edu.unal.sweng1.calzado.negocio;

import java.math.BigDecimal;
import java.util.List;

/**
 * Valor derivado calculado. Si no se pudo calcular, {@code valor} es nulo y
 * {@code datosFaltantes} indica los datos base ausentes o no numéricos.
 */
public record ValorDerivado(BigDecimal valor, List<String> datosFaltantes) {

	public boolean calculado() {
		return valor != null;
	}
}

package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CalculadoraDigitoVerificacionTest {

	private final CalculadoraDigitoVerificacion calculadora = new CalculadoraDigitoVerificacion();

	@Test
	void calcularDigitoDeVerificacionParaNitsDeEjemplo() {
		assertEquals(8, calculadora.calcular("900123456"));
		assertEquals(4, calculadora.calcular("800987654"));
		assertEquals(3, calculadora.calcular("899999063"));
	}

	@Test
	void esValidoComparaElDigitoDeVerificacion() {
		assertTrue(calculadora.esValido("900123456", 8));
		assertFalse(calculadora.esValido("900123456", 4));
	}

	@Test
	void calcularRechazaNitsInvalidosConMensajeEnEspanol() {
		assertMensajeEnEspanol(null);
		assertMensajeEnEspanol("");
		assertMensajeEnEspanol("90012A456");
		assertMensajeEnEspanol("1234567890123456");
	}

	private void assertMensajeEnEspanol(String nit) {
		IllegalArgumentException excepcion = assertThrows(
				IllegalArgumentException.class, () -> calculadora.calcular(nit));

		assertTrue(excepcion.getMessage().startsWith("El NIT"));
	}
}

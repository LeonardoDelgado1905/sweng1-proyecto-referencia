package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

class CalculadoraValoresDerivadosCoberturaTest {

	@Test
	void calculaCifYGravamenCuandoFaltaElIva() {
		// Arrange
		CalculadoraValoresDerivados calculadora = new CalculadoraValoresDerivados();

		// Act
		ValoresDerivados valores = calculadora.calcular("100", "10", "5", "10", null);

		// Assert
		assertEquals(new BigDecimal("115.00"), valores.cif().valor());
		assertEquals(new BigDecimal("11.50"), valores.gravamen().valor());
		assertFalse(valores.iva().calculado());
		assertEquals(List.of("IVA (%)"), valores.iva().datosFaltantes());
		assertFalse(valores.totalAPagar().calculado());
		assertEquals(List.of("IVA (%)"), valores.totalAPagar().datosFaltantes());
	}

	@Test
	void rechazaUnValorBaseNegativo() {
		// Arrange
		CalculadoraValoresDerivados calculadora = new CalculadoraValoresDerivados();

		// Act
		ValoresDerivados valores = calculadora.calcular("-1", "10", "5", "10", "19");

		// Assert
		assertFalse(valores.cif().calculado());
		assertEquals(List.of("Valor FOB"), valores.cif().datosFaltantes());
		assertFalse(valores.totalAPagar().calculado());
		assertEquals(List.of("Valor FOB"), valores.totalAPagar().datosFaltantes());
	}
}

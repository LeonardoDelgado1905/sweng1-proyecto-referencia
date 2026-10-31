package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

class CalculadoraValoresDerivadosTest {

	private final CalculadoraValoresDerivados calculadora = new CalculadoraValoresDerivados();

	@Test
	void calculaCifGravamenIvaYTotal() {
		ValoresDerivados valores = calculadora.calcular("2500.00", "350.00", "50.00", "10", "19");

		assertEquals(new BigDecimal("2900.00"), valores.cif().valor());
		assertEquals(new BigDecimal("290.00"), valores.gravamen().valor());
		assertEquals(new BigDecimal("606.10"), valores.iva().valor());
		assertEquals(new BigDecimal("896.10"), valores.totalAPagar().valor());
	}

	@Test
	void redondeaADosDecimalesHalfUp() {
		ValoresDerivados valores = calculadora.calcular("0.05", "0", "0", "10", "0");

		assertEquals(new BigDecimal("0.05"), valores.cif().valor());
		assertEquals(new BigDecimal("0.01"), valores.gravamen().valor());
	}

	@Test
	void sinDatoBaseNoCalculaYIndicaCualFalta() {
		ValoresDerivados valores = calculadora.calcular("2500.00", "350.00", "", "10", "19");

		assertFalse(valores.cif().calculado());
		assertEquals(List.of("Seguro"), valores.cif().datosFaltantes());
		assertFalse(valores.totalAPagar().calculado());
		assertEquals(List.of("Seguro"), valores.totalAPagar().datosFaltantes());
	}

	@Test
	void datoNoNumericoONuloNoCalculaSinLanzarError() {
		ValoresDerivados valores = calculadora.calcular("abc", "350.00", "50.00", null, "19");

		assertEquals(List.of("Valor FOB"), valores.cif().datosFaltantes());
		assertEquals(List.of("Valor FOB", "Gravamen (%)"), valores.gravamen().datosFaltantes());
	}

	@Test
	void calculaCifAunqueFalteElPorcentajeDeGravamen() {
		ValoresDerivados valores = calculadora.calcular("100", "10", "5", null, "19");

		assertEquals(new BigDecimal("115.00"), valores.cif().valor());
		assertFalse(valores.gravamen().calculado());
	}
}

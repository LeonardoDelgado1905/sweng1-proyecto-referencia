package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculadoraValoresDerivadosParametrizadaTest {

	@ParameterizedTest(name = "[{index}] CIF de {0} + {1} + {2} = {3}")
	@CsvSource({
			"0.005, 0, 0, 0.01",
			"1.115, 0, 0, 1.12",
			"0.004, 0, 0, 0.00",
			"2500.00, 350.00, 50.00, 2900.00",
			"1.002, 0.002, 0.001, 1.01"
	})
	void redondeaElCifADosDecimales(String fob, String flete, String seguro, String esperado) {
		// Arrange
		CalculadoraValoresDerivados calculadora = new CalculadoraValoresDerivados();

		// Act
		ValorDerivado cif = calculadora.calcular(fob, flete, seguro, "10", "19").cif();

		// Assert
		assertEquals(new BigDecimal(esperado), cif.valor());
	}

	@ParameterizedTest(name = "[{index}] Gravamen de CIF {0} al {1}% = {2}")
	@CsvSource({
			"100.05, 10, 10.01",
			"0.05, 10, 0.01",
			"0.049, 10, 0.00",
			"2900.00, 10, 290.00"
	})
	void redondeaElGravamenSobreElCifSinRedondear(String fob, String porcentaje, String esperado) {
		// Arrange
		CalculadoraValoresDerivados calculadora = new CalculadoraValoresDerivados();

		// Act
		ValorDerivado gravamen = calculadora.calcular(fob, "0", "0", porcentaje, "19").gravamen();

		// Assert
		assertEquals(new BigDecimal(esperado), gravamen.valor());
	}

	@ParameterizedTest(name = "[{index}] Dato no utilizable: {3} (FOB={0}, flete={1}, seguro={2})")
	// Una celda sin comillas es null; '' representa una cadena vacía.
	@CsvSource({
			", 10, 5, Valor FOB",
			"'', 10, 5, Valor FOB",
			"'   ', 10, 5, Valor FOB",
			"abc, 10, 5, Valor FOB",
			"-1, 10, 5, Valor FOB",
			"100, , 5, Flete",
			"100, '', 5, Flete",
			"100, '   ', 5, Flete",
			"100, abc, 5, Flete",
			"100, -1, 5, Flete",
			"100, 10, , Seguro",
			"100, 10, '', Seguro",
			"100, 10, '   ', Seguro",
			"100, 10, abc, Seguro",
			"100, 10, -1, Seguro"
	})
	void noCalculaElCifSiUnDatoBaseNoEsUtilizable(
			String fob, String flete, String seguro, String datoFaltante) {
		// Arrange
		CalculadoraValoresDerivados calculadora = new CalculadoraValoresDerivados();

		// Act
		ValorDerivado cif = calculadora.calcular(fob, flete, seguro, "10", "19").cif();

		// Assert
		assertFalse(cif.calculado());
		assertEquals(List.of(datoFaltante), cif.datosFaltantes());
	}
}

package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DeclaracionServiceConEspiaManualTest {

	@Test
	void validarSinCamposPendientesGuardaUnaVez() {
		// Arrange
		Long id = 1L;
		Declaracion declaracion = new Declaracion(LocalDate.of(2026, 1, 1));
		declaracion.agregarCampo(new CampoDeclaracion(
				"NIT proveedor", "900123456", null, 0.99, EstadoCampo.CONFIABLE, false));
		DeclaracionRepositoryEspia espia = new DeclaracionRepositoryEspia(id, declaracion);
		DeclaracionService service = new DeclaracionService(espia);

		// Act
		Declaracion resultado = service.validar(id);

		// Assert
		assertEquals(1, espia.getLlamadasGuardar());
		assertEquals(EstadoDeclaracion.VALIDADA, resultado.getEstado());
	}

	@Test
	void validarConCamposPendientesNoGuarda() {
		// Arrange
		Long id = 1L;
		Declaracion declaracion = new Declaracion(LocalDate.of(2026, 1, 1));
		declaracion.agregarCampo(new CampoDeclaracion(
				"Peso bruto", "120 kg / 125 kg", null, 0.6, EstadoCampo.CONFLICTO, false));
		DeclaracionRepositoryEspia espia = new DeclaracionRepositoryEspia(id, declaracion);
		DeclaracionService service = new DeclaracionService(espia);

		// Act y Assert
		assertThrows(DeclaracionNoValidableException.class, () -> service.validar(id));
		assertEquals(0, espia.getLlamadasGuardar());
		assertEquals(EstadoDeclaracion.BORRADOR, declaracion.getEstado());
	}
}

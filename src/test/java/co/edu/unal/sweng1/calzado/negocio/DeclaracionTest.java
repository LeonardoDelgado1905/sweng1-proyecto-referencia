package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DeclaracionTest {

	@Test
	void validarLanzaExcepcionConCamposPendientes() {
		Declaracion declaracion = new Declaracion(LocalDate.now());
		CampoDeclaracion pendiente = new CampoDeclaracion(
				"Peso bruto", "120", null, 0.6, EstadoCampo.CONFLICTO, false);
		declaracion.agregarCampo(pendiente);

		DeclaracionNoValidableException exception = assertThrows(
				DeclaracionNoValidableException.class, declaracion::validar);

		assertEquals(1, exception.getCamposPendientes().size());
		assertEquals(pendiente, exception.getCamposPendientes().getFirst());
		assertEquals(EstadoDeclaracion.BORRADOR, declaracion.getEstado());
	}

	@Test
	void validarCambiaEstadoCuandoNoHayCamposPendientes() {
		Declaracion declaracion = new Declaracion(LocalDate.now());
		declaracion.agregarCampo(new CampoDeclaracion(
				"NIT proveedor", "900123456", null, 0.99, EstadoCampo.CONFIABLE, false));

		declaracion.validar();

		assertEquals(EstadoDeclaracion.VALIDADA, declaracion.getEstado());
	}

	@Test
	void corregirAsignaValorYMarcaCampoComoRevisado() {
		CampoDeclaracion campo = new CampoDeclaracion(
				"Peso bruto", "120", null, 0.6, EstadoCampo.CONFLICTO, false);

		campo.corregir("125");

		assertEquals("125", campo.getValor());
		assertTrue(campo.isRevisado());
		assertFalse(campo.requiereRevision());
	}
}

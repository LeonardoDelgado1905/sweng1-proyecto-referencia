package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DeclaracionServiceTest {

	@Test
	void corregirCampoAceptaExplicitamenteElValorExistente() {
		DeclaracionRepositoryEnMemoria repository = new DeclaracionRepositoryEnMemoria();
		DeclaracionService service = new DeclaracionService(repository);
		Declaracion declaracion = new Declaracion(LocalDate.now());
		CampoDeclaracion campo = new CampoDeclaracion(
				"Peso bruto", "120 kg / 125 kg", null, 0.6, EstadoCampo.CONFLICTO, false);
		declaracion.agregarCampo(campo);
		Declaracion guardada = repository.guardar(declaracion);

		Declaracion resultado = service.corregirCampo(guardada.getId(), campo.getId(), campo.getValor());

		assertTrue(campo.isRevisado());
		assertEquals("120 kg / 125 kg", resultado.getCampos().getFirst().getValor());
	}

	@Test
	void validarGuardaElCambioDeEstado() {
		DeclaracionRepositoryEnMemoria repository = new DeclaracionRepositoryEnMemoria();
		DeclaracionService service = new DeclaracionService(repository);
		Declaracion declaracion = new Declaracion(LocalDate.now());
		declaracion.agregarCampo(new CampoDeclaracion(
				"NIT proveedor", "900123456", null, 0.99, EstadoCampo.CONFIABLE, false));
		Declaracion guardada = repository.guardar(declaracion);

		Declaracion resultado = service.validar(guardada.getId());

		assertEquals(EstadoDeclaracion.VALIDADA, resultado.getEstado());
		assertEquals(EstadoDeclaracion.VALIDADA, repository.buscarPorId(guardada.getId()).orElseThrow().getEstado());
	}
}

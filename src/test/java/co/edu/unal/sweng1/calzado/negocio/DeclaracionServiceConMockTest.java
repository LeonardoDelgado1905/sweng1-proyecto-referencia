package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.edu.unal.sweng1.calzado.persistencia.DeclaracionRepository;

// El mock devuelve respuestas programadas; el fake de DeclaracionServiceTest guarda datos en memoria.
@ExtendWith(MockitoExtension.class)
class DeclaracionServiceConMockTest {

	@Mock
	private DeclaracionRepository repository;

	@Test
	void validarSinCamposPendientesDejaLaDeclaracionValidada() {
		// Arrange
		Long id = 1L;
		Declaracion declaracion = new Declaracion(LocalDate.now());
		declaracion.agregarCampo(new CampoDeclaracion(
				"NIT proveedor", "900123456", null, 0.99, EstadoCampo.CONFIABLE, false));
		when(repository.buscarPorId(id)).thenReturn(Optional.of(declaracion));
		DeclaracionService service = new DeclaracionService(repository);

		// Act
		service.validar(id);

		// Assert
		assertEquals(EstadoDeclaracion.VALIDADA, declaracion.getEstado());
	}

	@Test
	void validarConCamposPendientesMantieneLaDeclaracionEnBorrador() {
		// Arrange
		Long id = 1L;
		Declaracion declaracion = new Declaracion(LocalDate.now());
		CampoDeclaracion campo = new CampoDeclaracion(
				"Peso bruto", "120 kg / 125 kg", null, 0.6, EstadoCampo.CONFLICTO, false);
		declaracion.agregarCampo(campo);
		when(repository.buscarPorId(id)).thenReturn(Optional.of(declaracion));
		DeclaracionService service = new DeclaracionService(repository);

		// Act
		DeclaracionNoValidableException excepcion =
				assertThrows(DeclaracionNoValidableException.class, () -> service.validar(id));

		// Assert
		assertTrue(excepcion.getCamposPendientes().contains(campo));
		assertEquals(EstadoDeclaracion.BORRADOR, declaracion.getEstado());
	}
}

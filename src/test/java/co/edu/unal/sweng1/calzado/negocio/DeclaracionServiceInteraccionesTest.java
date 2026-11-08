package co.edu.unal.sweng1.calzado.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.edu.unal.sweng1.calzado.persistencia.DeclaracionRepository;

@ExtendWith(MockitoExtension.class)
class DeclaracionServiceInteraccionesTest {

	@Mock
	private DeclaracionRepository repository;

	// Verificar estado comprueba el resultado; conviene para las reglas de negocio.
	// Verificar comportamiento comprueba el uso de colaboradores; conviene para asegurar interacciones importantes.

	@Test
	void validarGuardaUnaVezLaMismaDeclaracionValidada() {
		// Arrange
		Long id = 1L;
		Declaracion declaracion = new Declaracion(LocalDate.of(2026, 1, 1));
		declaracion.agregarCampo(new CampoDeclaracion(
				"NIT proveedor", "900123456", null, 0.99, EstadoCampo.CONFIABLE, false));
		when(repository.buscarPorId(id)).thenReturn(Optional.of(declaracion));
		DeclaracionService service = new DeclaracionService(repository);

		// Act
		service.validar(id);

		// Assert
		ArgumentCaptor<Declaracion> captor = ArgumentCaptor.forClass(Declaracion.class);
		verify(repository, times(1)).guardar(captor.capture());
		assertSame(declaracion, captor.getValue());
		assertEquals(EstadoDeclaracion.VALIDADA, captor.getValue().getEstado());
	}

	@Test
	void validarConCamposPendientesLanzaExcepcionYNoGuarda() {
		// Arrange
		Long id = 2L;
		Declaracion declaracion = new Declaracion(LocalDate.of(2026, 1, 1));
		declaracion.agregarCampo(new CampoDeclaracion(
				"Peso bruto", "120 kg / 125 kg", null, 0.6, EstadoCampo.CONFLICTO, false));
		when(repository.buscarPorId(id)).thenReturn(Optional.of(declaracion));
		DeclaracionService service = new DeclaracionService(repository);

		// Act
		assertThrows(DeclaracionNoValidableException.class, () -> service.validar(id));

		// Assert
		verify(repository, never()).guardar(any());
	}
}

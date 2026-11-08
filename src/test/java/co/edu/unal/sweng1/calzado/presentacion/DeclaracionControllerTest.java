package co.edu.unal.sweng1.calzado.presentacion;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import co.edu.unal.sweng1.calzado.negocio.CampoDeclaracion;
import co.edu.unal.sweng1.calzado.negocio.Declaracion;
import co.edu.unal.sweng1.calzado.negocio.DeclaracionNoValidableException;
import co.edu.unal.sweng1.calzado.negocio.DeclaracionService;
import co.edu.unal.sweng1.calzado.negocio.EstadoCampo;

@WebMvcTest(DeclaracionController.class)
class DeclaracionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DeclaracionService declaracionService;

	@Test
	void consultarDeclaracionMuestraLaVistaYElModelo() throws Exception {
		// Arrange
		Declaracion declaracion = nuevaDeclaracion();
		when(declaracionService.buscar(1L)).thenReturn(declaracion);

		// Act
		ResultActions resultado = mockMvc.perform(get("/declaraciones/1"));

		// Assert
		resultado.andExpect(status().isOk())
				.andExpect(view().name("declaracion"))
				.andExpect(model().attribute("declaracion", sameInstance(declaracion)))
				.andExpect(model().attribute("valores", notNullValue()))
				.andExpect(model().attribute("camposPendientes", nullValue()));
		verify(declaracionService).buscar(1L);
	}

	@Test
	void validarConCamposPendientesMuestraLaVistaYElAviso() throws Exception {
		// Arrange
		Declaracion declaracion = nuevaDeclaracion();
		CampoDeclaracion pendiente = new CampoDeclaracion(
				"Peso bruto", "120", null, 0.6, EstadoCampo.CONFLICTO, false);
		ReflectionTestUtils.setField(pendiente, "id", 1L);
		declaracion.agregarCampo(pendiente);
		when(declaracionService.validar(1L))
				.thenThrow(new DeclaracionNoValidableException(List.of(pendiente)));
		when(declaracionService.buscar(1L)).thenReturn(declaracion);

		// Act
		ResultActions resultado = mockMvc.perform(post("/declaraciones/1/validar"));

		// Assert
		resultado.andExpect(status().isOk())
				.andExpect(view().name("declaracion"))
				.andExpect(model().attribute("camposPendientes", hasItem(pendiente)))
				.andExpect(content().string(containsString("Hay campos pendientes de revisión")));
		verify(declaracionService).validar(1L);
		verify(declaracionService).buscar(1L);
	}

	@Test
	void validarSinCamposPendientesRedirigeAlDetalle() throws Exception {
		// Arrange
		Declaracion declaracion = nuevaDeclaracion();
		when(declaracionService.validar(1L)).thenReturn(declaracion);

		// Act
		ResultActions resultado = mockMvc.perform(post("/declaraciones/1/validar"));

		// Assert
		resultado.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/declaraciones/1"));
		verify(declaracionService).validar(1L);
	}

	private Declaracion nuevaDeclaracion() {
		Declaracion declaracion = new Declaracion(LocalDate.of(2026, 1, 1));
		// La plantilla usa el id en las rutas y la entidad no ofrece un asignador público.
		ReflectionTestUtils.setField(declaracion, "id", 1L);
		return declaracion;
	}
}

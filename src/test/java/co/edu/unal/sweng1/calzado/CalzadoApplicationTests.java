package co.edu.unal.sweng1.calzado;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CalzadoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void permiteRevisarCorregirYValidarUnaDeclaracionPrellenada() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"))
				.andExpect(content().string(containsString("BORRADOR")))
				.andExpect(content().string(containsString("Ver declaración")));

		mockMvc.perform(get("/declaraciones/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("declaracion"))
				.andExpect(content().string(containsString("Peso bruto")))
				.andExpect(content().string(containsString("requiere-revision")));

		mockMvc.perform(post("/declaraciones/1/validar"))
				.andExpect(status().isOk())
				.andExpect(view().name("declaracion"))
				.andExpect(content().string(containsString("Hay campos pendientes de revisión")));

		mockMvc.perform(post("/declaraciones/1/campos/4").param("valor", "120 kg"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/declaraciones/1"));
		mockMvc.perform(post("/declaraciones/1/campos/5").param("valor", "6403999000"))
				.andExpect(status().is3xxRedirection());
		mockMvc.perform(post("/declaraciones/1/campos/6").param("valor", "25.00"))
				.andExpect(status().is3xxRedirection());

		mockMvc.perform(post("/declaraciones/1/validar"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/declaraciones/1"));
		mockMvc.perform(get("/declaraciones/1"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("VALIDADA")));
	}
}

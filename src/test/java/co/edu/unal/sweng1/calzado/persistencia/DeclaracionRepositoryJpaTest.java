package co.edu.unal.sweng1.calzado.persistencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class DeclaracionRepositoryJpaTest {

	@Autowired
	private DeclaracionRepository repository;

	@Test
	@Transactional
	void cargaLasDeclaracionesInicialesDesdeDataSql() {
		var declaraciones = repository.buscarTodas();

		assertEquals(2, declaraciones.size());
		assertEquals(3, declaraciones.getFirst().getDocumentos().size());
		assertEquals(6, declaraciones.getFirst().getCampos().size());
		assertFalse(declaraciones.getFirst().getCampos().getFirst().requiereRevision());
	}
}

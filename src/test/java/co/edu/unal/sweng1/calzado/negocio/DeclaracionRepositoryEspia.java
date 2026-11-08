package co.edu.unal.sweng1.calzado.negocio;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import co.edu.unal.sweng1.calzado.persistencia.DeclaracionRepository;

// Un spy de Meszaros sustituye al colaborador y registra interacciones para que la prueba las consulte.
class DeclaracionRepositoryEspia implements DeclaracionRepository {

	private final Long id;
	private final Declaracion declaracionPreparada;
	private int llamadasGuardar;

	DeclaracionRepositoryEspia(Long id, Declaracion declaracionPreparada) {
		this.id = id;
		this.declaracionPreparada = declaracionPreparada;
	}

	@Override
	public List<Declaracion> buscarTodas() {
		// Este ejemplo solo necesita buscar por identificador.
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<Declaracion> buscarPorId(Long id) {
		return Objects.equals(this.id, id) ? Optional.of(declaracionPreparada) : Optional.empty();
	}

	@Override
	public Declaracion guardar(Declaracion declaracion) {
		llamadasGuardar++;
		return declaracion;
	}

	int getLlamadasGuardar() {
		return llamadasGuardar;
	}
}

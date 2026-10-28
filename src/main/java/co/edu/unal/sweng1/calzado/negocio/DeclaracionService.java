package co.edu.unal.sweng1.calzado.negocio;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unal.sweng1.calzado.persistencia.DeclaracionRepository;

@Service
public class DeclaracionService {

	private final DeclaracionRepository repository;

	public DeclaracionService(DeclaracionRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public List<Declaracion> listar() {
		return repository.buscarTodas();
	}

	@Transactional(readOnly = true)
	public Declaracion buscar(Long id) {
		return repository.buscarPorId(id)
				.orElseThrow(() -> new DeclaracionNoEncontradaException(id));
	}

	@Transactional
	public Declaracion corregirCampo(Long declaracionId, Long campoId, String valor) {
		Declaracion declaracion = buscar(declaracionId);
		CampoDeclaracion campo = declaracion.getCampos().stream()
				.filter(c -> campoId.equals(c.getId()))
				.findFirst()
				.orElseThrow(() -> new CampoNoEncontradoException(campoId));
		campo.corregir(valor);
		return repository.guardar(declaracion);
	}

	@Transactional
	public Declaracion validar(Long id) {
		Declaracion declaracion = buscar(id);
		declaracion.validar();
		return repository.guardar(declaracion);
	}
}

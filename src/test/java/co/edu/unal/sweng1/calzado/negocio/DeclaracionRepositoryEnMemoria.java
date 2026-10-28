package co.edu.unal.sweng1.calzado.negocio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import co.edu.unal.sweng1.calzado.persistencia.DeclaracionRepository;

class DeclaracionRepositoryEnMemoria implements DeclaracionRepository {

	private final Map<Long, Declaracion> declaraciones = new LinkedHashMap<>();
	private long siguienteId = 1;

	@Override
	public List<Declaracion> buscarTodas() {
		return new ArrayList<>(declaraciones.values());
	}

	@Override
	public Optional<Declaracion> buscarPorId(Long id) {
		return Optional.ofNullable(declaraciones.get(id));
	}

	@Override
	public Declaracion guardar(Declaracion declaracion) {
		if (declaracion.getId() == null) {
			declaracion.asignarId(siguienteId++);
			for (DocumentoSoporte documento : declaracion.getDocumentos()) {
				documento.asignarId(siguienteId++);
			}
			for (CampoDeclaracion campo : declaracion.getCampos()) {
				campo.asignarId(siguienteId++);
			}
		}
		declaraciones.put(declaracion.getId(), declaracion);
		return declaracion;
	}
}

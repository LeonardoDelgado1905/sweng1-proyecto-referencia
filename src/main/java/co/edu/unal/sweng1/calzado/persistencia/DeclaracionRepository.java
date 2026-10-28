package co.edu.unal.sweng1.calzado.persistencia;

import java.util.List;
import java.util.Optional;

import co.edu.unal.sweng1.calzado.negocio.Declaracion;

public interface DeclaracionRepository {

	List<Declaracion> buscarTodas();

	Optional<Declaracion> buscarPorId(Long id);

	Declaracion guardar(Declaracion declaracion);
}

package co.edu.unal.sweng1.calzado.persistencia;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import co.edu.unal.sweng1.calzado.negocio.Declaracion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class DeclaracionRepositoryJpa implements DeclaracionRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Declaracion> buscarTodas() {
		return entityManager.createQuery(
				"select d from Declaracion d order by d.id", Declaracion.class)
				.getResultList();
	}

	@Override
	public Optional<Declaracion> buscarPorId(Long id) {
		return Optional.ofNullable(entityManager.find(Declaracion.class, id));
	}

	@Override
	public Declaracion guardar(Declaracion declaracion) {
		if (declaracion.getId() == null) {
			entityManager.persist(declaracion);
			return declaracion;
		}
		return entityManager.merge(declaracion);
	}
}

package co.edu.unal.sweng1.calzado.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "declaracion")
public class Declaracion {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "declaracion_seq")
	@SequenceGenerator(name = "declaracion_seq", sequenceName = "declaracion_seq", allocationSize = 1)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoDeclaracion estado;

	@Column(name = "fecha_creacion", nullable = false)
	private LocalDate fechaCreacion;

	@OneToMany(mappedBy = "declaracion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocumentoSoporte> documentos = new ArrayList<>();

	@OneToMany(mappedBy = "declaracion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CampoDeclaracion> campos = new ArrayList<>();

	protected Declaracion() {
	}

	public Declaracion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
		this.estado = EstadoDeclaracion.BORRADOR;
	}

	public void agregarDocumento(DocumentoSoporte documento) {
		documentos.add(documento);
		documento.asociarA(this);
	}

	public void agregarCampo(CampoDeclaracion campo) {
		campos.add(campo);
		campo.asociarA(this);
	}

	public void validar() {
		List<CampoDeclaracion> pendientes = campos.stream()
				.filter(CampoDeclaracion::requiereRevision)
				.toList();
		if (!pendientes.isEmpty()) {
			throw new DeclaracionNoValidableException(pendientes);
		}
		estado = EstadoDeclaracion.VALIDADA;
	}

	public Long getId() {
		return id;
	}

	public EstadoDeclaracion getEstado() {
		return estado;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public List<DocumentoSoporte> getDocumentos() {
		return documentos;
	}

	public List<CampoDeclaracion> getCampos() {
		return campos;
	}
}

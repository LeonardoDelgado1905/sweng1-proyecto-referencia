package co.edu.unal.sweng1.calzado.negocio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "campo_declaracion")
public class CampoDeclaracion {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campo_declaracion_seq")
	@SequenceGenerator(name = "campo_declaracion_seq", sequenceName = "campo_declaracion_seq", allocationSize = 1)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "declaracion_id", nullable = false)
	private Declaracion declaracion;

	@ManyToOne
	@JoinColumn(name = "origen_id")
	private DocumentoSoporte origen;

	@Column(nullable = false)
	private String nombre;

	@Column
	private String valor;

	@Column(name = "nivel_confianza")
	private Double nivelConfianza;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoCampo estado;

	@Column(nullable = false)
	private boolean revisado;

	protected CampoDeclaracion() {
	}

	public CampoDeclaracion(String nombre, String valor, DocumentoSoporte origen,
			Double nivelConfianza, EstadoCampo estado, boolean revisado) {
		this.nombre = nombre;
		this.valor = valor;
		this.origen = origen;
		this.nivelConfianza = nivelConfianza;
		this.estado = estado;
		this.revisado = revisado;
	}

	void asociarA(Declaracion declaracion) {
		this.declaracion = declaracion;
	}

	public void corregir(String valor) {
		if (valor == null || valor.trim().isEmpty()) {
			throw new CorreccionVaciaException();
		}
		this.valor = valor;
		this.revisado = true;
	}

	public boolean requiereRevision() {
		return estado != EstadoCampo.CONFIABLE && !revisado;
	}

	public Long getId() {
		return id;
	}

	void asignarId(Long id) {
		this.id = id;
	}

	public Declaracion getDeclaracion() {
		return declaracion;
	}

	public DocumentoSoporte getOrigen() {
		return origen;
	}

	public String getNombre() {
		return nombre;
	}

	public String getValor() {
		return valor;
	}

	public Double getNivelConfianza() {
		return nivelConfianza;
	}

	public EstadoCampo getEstado() {
		return estado;
	}

	public boolean isRevisado() {
		return revisado;
	}
}

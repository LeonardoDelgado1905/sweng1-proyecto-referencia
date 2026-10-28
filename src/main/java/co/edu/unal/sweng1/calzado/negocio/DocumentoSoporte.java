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
@Table(name = "documento_soporte")
public class DocumentoSoporte {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento_soporte_seq")
	@SequenceGenerator(name = "documento_soporte_seq", sequenceName = "documento_soporte_seq", allocationSize = 1)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "declaracion_id", nullable = false)
	private Declaracion declaracion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoDocumento tipo;

	@Column(nullable = false)
	private String archivo;

	protected DocumentoSoporte() {
	}

	public DocumentoSoporte(TipoDocumento tipo, String archivo) {
		this.tipo = tipo;
		this.archivo = archivo;
	}

	void asociarA(Declaracion declaracion) {
		this.declaracion = declaracion;
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

	public TipoDocumento getTipo() {
		return tipo;
	}

	public String getArchivo() {
		return archivo;
	}
}

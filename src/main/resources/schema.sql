CREATE SEQUENCE IF NOT EXISTS declaracion_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS documento_soporte_seq START WITH 100;
CREATE SEQUENCE IF NOT EXISTS campo_declaracion_seq START WITH 100;

CREATE TABLE IF NOT EXISTS declaracion (
	id BIGINT PRIMARY KEY,
	estado VARCHAR(30) NOT NULL,
	fecha_creacion DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS documento_soporte (
	id BIGINT PRIMARY KEY,
	declaracion_id BIGINT NOT NULL,
	tipo VARCHAR(40) NOT NULL,
	archivo VARCHAR(255) NOT NULL,
	CONSTRAINT fk_documento_declaracion
		FOREIGN KEY (declaracion_id) REFERENCES declaracion(id)
);

CREATE TABLE IF NOT EXISTS campo_declaracion (
	id BIGINT PRIMARY KEY,
	declaracion_id BIGINT NOT NULL,
	origen_id BIGINT,
	nombre VARCHAR(255) NOT NULL,
	valor VARCHAR(255),
	nivel_confianza DOUBLE,
	estado VARCHAR(40) NOT NULL,
	revisado BOOLEAN NOT NULL,
	CONSTRAINT fk_campo_declaracion
		FOREIGN KEY (declaracion_id) REFERENCES declaracion(id),
	CONSTRAINT fk_campo_origen
		FOREIGN KEY (origen_id) REFERENCES documento_soporte(id)
);

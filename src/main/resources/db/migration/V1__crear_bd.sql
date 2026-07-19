-- CREAR TABLA LIBRO
CREATE TABLE libro (
    id_libro BIGINT GENERATED ALWAYS AS IDENTITY,
    titulo VARCHAR(120) NOT NULL,
    autor VARCHAR(60) NOT NULL,
    opinion VARCHAR(500) NOT NULL,
    valoracion NUMERIC(2,1) NOT NULL,
    genero VARCHAR(20) NOT NULL,
    fecha_actualizacion DATE DEFAULT CURRENT_DATE,
    fecha_ingreso DATE NOT NULL,

    CONSTRAINT pk_libro PRIMARY KEY (id_libro)
);

-- CREAR TABLA ETIQUETA
CREATE TABLE etiqueta (
    id_etiqueta BIGINT GENERATED ALWAYS AS IDENTITY,
    descripcion VARCHAR(20) NOT NULL,
    id_libro BIGINT NOT NULL,

    CONSTRAINT pk_etiqueta PRIMARY KEY (id_etiqueta),
    CONSTRAINT fk_etiqueta_libro
        FOREIGN KEY (id_libro)
        REFERENCES libro(id_libro)
);

-- CREAR CHECK LONGITUD DE OPINION
ALTER TABLE libro
ADD CONSTRAINT ck_longitud_opinion
CHECK (LENGTH(opinion) >= 10);

-- CREAR CHECK ESCALA DE VALORACIÓN
ALTER TABLE libro
ADD CONSTRAINT ck_escala_valoracion
CHECK (valoracion BETWEEN 1.0 AND 5.0);

-- CREAR CHECK TIPOS DE GÉNERO
ALTER TABLE libro
ADD CONSTRAINT ck_tipo_genero
CHECK (genero IN ('LITERARIO', 'NO_LITERARIO'));
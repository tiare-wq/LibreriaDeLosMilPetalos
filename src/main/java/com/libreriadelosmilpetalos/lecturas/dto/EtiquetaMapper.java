package com.libreriadelosmilpetalos.lecturas.dto;

import com.libreriadelosmilpetalos.lecturas.entity.Etiqueta;
import com.libreriadelosmilpetalos.lecturas.entity.Libro;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EtiquetaMapper {

    public static EtiquetaDTO toDTO(Etiqueta entidad) {

        log.debug("Mapeando entidad a dto: {}", entidad);

        EtiquetaDTO dto = new EtiquetaDTO();

        dto.setDescripcion(entidad.getDescripcion());

        return dto;
    }

    public static Etiqueta toEntidad(Libro libro, EtiquetaDTO dto) {

        log.debug("Mapeando dto a entidad, libro: {}, dto: {}", libro, dto);

        Etiqueta entidad = new Etiqueta();

        entidad.setDescripcion(dto.getDescripcion());
        entidad.setLibro(libro);

        return entidad;
    }
}

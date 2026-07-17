package com.libreriadelosmilpetalos.lecturas.dto;

import java.util.List;

import com.libreriadelosmilpetalos.lecturas.entity.Libro;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LibroMapper {

    public static LibroDTO toDTO(Libro entidad) {

        log.debug("mapeando entidad a dto {}", entidad);

        LibroDTO dto = new LibroDTO();

        dto.setTitulo(entidad.getTitulo());
        dto.setAutor(entidad.getAutor());
        dto.setOpinion(entidad.getOpinion());
        dto.setValoracion(entidad.getValoracion());
        dto.setGenero(entidad.getGenero());
        dto.setFechaActualizacion(entidad.getFechaActualizacion());
        dto.setFechaIngreso(entidad.getFechaIngreso());
        dto.setEtiquetas(entidad.getEtiquetas()
            .stream()
            .map(e -> EtiquetaMapper.toDTO(e))
            .toList());

        return dto;
    }

    public static LibroDTO toDTO(Libro entidad, List<EtiquetaDTO> etiquetas) {

        log.debug("Mapeando entidad a dto. entidad: {}, etiquetas: {}", entidad, etiquetas);

        LibroDTO dto = new LibroDTO();

        dto.setTitulo(entidad.getTitulo());
        dto.setAutor(entidad.getAutor());
        dto.setOpinion(entidad.getOpinion());
        dto.setValoracion(entidad.getValoracion());
        dto.setGenero(entidad.getGenero());
        dto.setFechaActualizacion(entidad.getFechaActualizacion());
        dto.setFechaIngreso(entidad.getFechaIngreso());
        dto.setEtiquetas(etiquetas);

        return dto;
    }

    public static Libro toEntidad(LibroDTO dto) {

        log.debug("Mapendo dto a entidad: {}", dto);

        Libro entidad = new Libro();

        entidad.setTitulo(dto.getTitulo());
        entidad.setAutor(dto.getAutor());
        entidad.setOpinion(dto.getOpinion());
        entidad.setValoracion(dto.getValoracion());
        entidad.setGenero(dto.getGenero());
        entidad.setFechaActualizacion(dto.getFechaActualizacion());
        entidad.setFechaIngreso(dto.getFechaIngreso());

        return entidad;
    }

    public static Libro update(Libro entidad, LibroDTO dto) {
        log.debug("Actualizando entidad {} por dto {}", entidad, dto);

        entidad.setTitulo(dto.getTitulo());
        entidad.setAutor(dto.getAutor());
        entidad.setOpinion(dto.getOpinion());
        entidad.setValoracion(dto.getValoracion());
        entidad.setGenero(dto.getGenero());
        entidad.setFechaActualizacion(dto.getFechaActualizacion());
        entidad.setFechaIngreso(dto.getFechaIngreso());

        return entidad;
    }

}

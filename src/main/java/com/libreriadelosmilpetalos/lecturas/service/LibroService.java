package com.libreriadelosmilpetalos.lecturas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.libreriadelosmilpetalos.lecturas.dto.EtiquetaDTO;
import com.libreriadelosmilpetalos.lecturas.dto.GeneroEtiquetaDTO;
import com.libreriadelosmilpetalos.lecturas.dto.LibroDTO;
import com.libreriadelosmilpetalos.lecturas.dto.LibroMapper;
import com.libreriadelosmilpetalos.lecturas.entity.Etiqueta;
import com.libreriadelosmilpetalos.lecturas.entity.Libro;
import com.libreriadelosmilpetalos.lecturas.entity.RepoEtiquetas;
import com.libreriadelosmilpetalos.lecturas.exception.ResourceAlreadyExistsException;
import com.libreriadelosmilpetalos.lecturas.exception.ResourceNotFoundException;
import com.libreriadelosmilpetalos.lecturas.repository.LibroRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LibroService {

    private final LibroRepository repo;
    private final EtiquetaService etiquetaService;

    // ==========================================
    // MÉTODOS DE AGREGAR
    // ==========================================

    public LibroDTO agregarLibro(LibroDTO dto) {
        log.info("Creando libro");
        log.debug("dto: {}", dto);

        dto = normalizarLibro(dto);

        log.debug("Validando que el título sea único");
        if (repo.existsByTituloIgnoreCase(dto.getTitulo())) {
            throw new ResourceAlreadyExistsException("El libro ya se encuentra ingresado");
        }

        dto.setFechaIngreso(LocalDate.now());

        Libro entidad = LibroMapper.toEntidad(dto);
        entidad = repo.save(entidad);

        // Agrega etiquetas
        List<Etiqueta> entidades = etiquetaService.normalizarEtiquetas(entidad, dto.getEtiquetas());
        List<EtiquetaDTO> etiquetas = etiquetaService.agregarEtiquetas(entidades);

        return LibroMapper.toDTO(entidad, etiquetas);
    }

    // ==========================================
    // MÉTODOS DE BÚSQUEDA
    // ==========================================

    public Page<LibroDTO> mostrarTodos(Pageable pageable) {
        log.info("Iniciando búsqueda de todos los libros");

        Page<Libro> libros = repo.findAll(pageable);

        return libros
            .map(LibroMapper::toDTO);
    }

    public LibroDTO buscarPorTitulo(String titulo) {
        log.info("Buscando libro {}", titulo);

        String ttlNormalizado = normalizar(titulo);

        Libro libro = repo.findByTituloIgnoreCase(titulo)
            .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el libro: " + ttlNormalizado));
            
        return LibroMapper.toDTO(libro);
    }

    // ==========================================
    // MÉTODOS DE BÚSQUEDA PERSONALIZADA
    // ==========================================

    public Page<LibroDTO> buscarPorTituloAutor(String texto, Pageable pageable) {
        log.info("Inicia la búsqueda por título");
        log.debug("texto: {}", texto);

        String busqueda = normalizar(texto);

        return repo.findWhenContainingText(busqueda, pageable)
            .map(LibroMapper::toDTO);
    }

    public Page<LibroDTO> buscarPorGeneroEtiqueta(GeneroEtiquetaDTO filtros, Pageable pageable) {
        log.info("Inicia la búsqueda por género o etiquetas");
        log.debug("filtros: {}", filtros);

        List<EtiquetaDTO> etiq = filtros.getEtiquetas();
        List<RepoEtiquetas> etiqDesc;

        if (etiq != null && !etiq.isEmpty()) {
            etiqDesc = etiq
            .stream()
            .map(EtiquetaDTO::getDescripcion)
            .toList();
        } else {
            etiqDesc = new ArrayList<>();
        }

        return repo.findByGeneroAndEtiqueta(filtros.getGeneros(), etiqDesc, etiqDesc.size(), pageable)
            .map(LibroMapper::toDTO);
    }

    public Page<LibroDTO> buscarEntreFechas(LocalDate inicio, LocalDate termino, Pageable pageable) {
        log.info("Buscando libros ingresados entre fechas");
        log.debug("inicio: {}, termino: {}", inicio, termino);

        if (inicio.isAfter(termino)) {
            throw new IllegalArgumentException("La fecha de inicio es posterior a la fecha de término");
        } else if(inicio.isBefore(LocalDate.parse("01/01/2026"))) {
            throw new IllegalArgumentException("No hay registros disponibles antes de la fecha 2026");
        } else if (termino.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("El rango de fechas no es válido");
        }

        return repo.findByFechaIngresoBetween(inicio, termino, pageable)
            .map(LibroMapper::toDTO);
    }

    // ==========================================
    // MÉTODOS DE ACTUALIZACIÓN
    // ==========================================

    public LibroDTO actualizarLibro(LibroDTO dto) {
        log.info("Actualizando libro");
        log.debug("dto: {}", dto);

        LibroDTO libro = normalizarLibro(dto);

        Libro entidad = repo.findByTituloIgnoreCase(dto.getTitulo())
            .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el libro: " + libro.getTitulo()));

        dto.setFechaActualizacion(LocalDate.now());

        List<EtiquetaDTO> etiquetas = etiquetaService.actualizarEtiqueta(entidad, dto.getEtiquetas());

        entidad = LibroMapper.update(entidad, dto);

        repo.save(entidad);

        return LibroMapper.toDTO(entidad, etiquetas);
        
    }

    // ==========================================
    // MÉTODOS DE ELIMINACIÓN
    // ==========================================

    public void eliminarLibroPorTitulo(String titulo) {
        log.info("Eliminando el libro {}", titulo);

        String tituloNormalizado = normalizar(titulo);

        Libro entidad = repo.findByTituloIgnoreCase(tituloNormalizado)
            .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el libro: " + tituloNormalizado));

        repo.delete(entidad);
    }

    // ============================================
    // MÉTODOS ADICIONALES
    // ============================================

    // Revisa y corrige espacios de una cadena de texto
    public String normalizar(String text) {
        if (text == null || text.isBlank()) {
            return text;
        }

        return text
            .trim()
            .replaceAll("-+", " ")
            .replaceAll("\\s+-", " ");
    }
    
    public LibroDTO normalizarLibro(LibroDTO dto) {
        log.info("Iniciando limpieza del formato");

        // Corrige espacios irregulares
        String titulo = normalizar(dto.getTitulo());
        String autor = normalizar(dto.getAutor());
        String opinion = normalizar(dto.getOpinion());

        dto.setTitulo(titulo);
        dto.setAutor(autor);
        dto.setOpinion(opinion);

        return dto;

    }

}

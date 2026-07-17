package com.libreriadelosmilpetalos.lecturas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.libreriadelosmilpetalos.lecturas.dto.EtiquetaDTO;
import com.libreriadelosmilpetalos.lecturas.dto.EtiquetaMapper;
import com.libreriadelosmilpetalos.lecturas.entity.Etiqueta;
import com.libreriadelosmilpetalos.lecturas.entity.Libro;
import com.libreriadelosmilpetalos.lecturas.repository.EtiquetaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EtiquetaService {

    private final EtiquetaRepository repo;

    // ==========================================
    // MÉTODOS DE AGREGAR
    // ==========================================
    
    public List<EtiquetaDTO> agregarEtiquetas(List<Etiqueta> entidades) {

        log.debug("Agregando etiquetas: {}", entidades);

        if (entidades.size() > 10) {
            throw new IllegalArgumentException("Un libro no puede tener más de 10 etiquetas");
        }

        entidades = repo.saveAll(entidades);

        List<EtiquetaDTO> almacenadas = entidades.stream()
            .map(EtiquetaMapper::toDTO)
            .toList();

        return almacenadas;
    }

    // ==========================================
    // MÉTODOS DE ACTUALIZACIÓN
    // ==========================================

    public List<EtiquetaDTO> actualizarEtiqueta(Libro libro, List<EtiquetaDTO> etiquetas) {
        log.info("Actualizando etiquetas");
        log.debug("etiquetas: {}", etiquetas);

        List<Etiqueta> entidad = normalizarEtiquetas(libro, etiquetas);

        List<Etiqueta> nuevas = new ArrayList<>();
        List<Etiqueta> actuales = new ArrayList<>(libro.getEtiquetas());
        List<Etiqueta> eliminadas = new ArrayList<>(libro.getEtiquetas());

        for (Etiqueta et : entidad) {
            if (actuales.contains(et)) {
                eliminadas.remove(et);
            } else {
                nuevas.add(et);
            }
        }

        if (!nuevas.isEmpty()) {
            agregarEtiquetas(nuevas);
        }
        actuales.addAll(nuevas);

        if (!eliminadas.isEmpty()) {
            eliminarEtiquetas(eliminadas);
        }
        actuales.removeAll(eliminadas);

        if (actuales.size() > 10) {
            throw new IllegalArgumentException("Un libro no puede tener más de 10 etiquetas");
        }

        return actuales
            .stream()
            .map(EtiquetaMapper::toDTO)
            .toList();
    }

    // ==========================================
    // MÉTODOS DE ELIMINACIÓN
    // ==========================================
    
    public void eliminarEtiquetas(List<Etiqueta> etiquetas) {
        log.debug("Eliminando etiquetas {}", etiquetas);

        repo.deleteAll(etiquetas);
    }

    // ============================================
    // MÉTODOS ADICIONALES
    // ============================================

    public List<Etiqueta> normalizarEtiquetas(Libro libro, List<EtiquetaDTO> etiquetas) {
        if (etiquetas == null || etiquetas.isEmpty()) {
            return null;
        }

        Set<EtiquetaDTO> noRepetidos = Set.copyOf(etiquetas);

        List<Etiqueta> entidades = noRepetidos.stream()
            .map(dto -> EtiquetaMapper.toEntidad(libro, dto))
            .toList();

        return entidades;
    }
}

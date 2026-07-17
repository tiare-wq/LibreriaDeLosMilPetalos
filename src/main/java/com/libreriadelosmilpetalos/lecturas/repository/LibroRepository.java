package com.libreriadelosmilpetalos.lecturas.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.libreriadelosmilpetalos.lecturas.entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTituloIgnoreCase(String titulo);

    @EntityGraph(attributePaths = {"etiquetas"})
    Optional<Libro> findByTituloIgnoreCase(String titulo);

    @EntityGraph(attributePaths = {"etiquetas"})
    Page<Libro> findAllPage(Pageable pageable);
}
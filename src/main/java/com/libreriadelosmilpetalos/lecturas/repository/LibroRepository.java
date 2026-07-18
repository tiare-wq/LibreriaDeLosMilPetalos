package com.libreriadelosmilpetalos.lecturas.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.libreriadelosmilpetalos.lecturas.entity.Genero;
import com.libreriadelosmilpetalos.lecturas.entity.Libro;
import com.libreriadelosmilpetalos.lecturas.entity.RepoEtiquetas;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTituloIgnoreCase(String titulo);

    @EntityGraph(attributePaths = {"etiquetas"})
    Page<Libro> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"etiquetas"})
    Page<Libro> findByFechaIngresoBetween(LocalDate inicio, LocalDate termino, Pageable pageable);

    @EntityGraph(attributePaths = {"etiquetas"})
    @Query("""
    SELECT l
    FROM Libro l
    HAVING COUNT(SELECT *
        FROM etiquetas
        WHERE)
    JOIN FETCH l.etiquetas e
    WHERE l.etiquetas ALL :filtros
    AND l.genero LIKE :filtros
    """)
    Page<Libro> findByGeneroAndEtiqueta(List<Genero> generos, List<RepoEtiquetas> etiquetas, Pageable pageable);
    
    @EntityGraph(attributePaths = {"etiquetas"})
    Optional<Libro> findByTituloIgnoreCase(String titulo);

    @EntityGraph(attributePaths = {"etiquetas"})
    @Query("""
    SELECT l
    FROM Libro l
    WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :texto, '%'))
    """)
    Page<Libro> findWhenContainingText(@Param("texto") String texto, Pageable pageable);
}
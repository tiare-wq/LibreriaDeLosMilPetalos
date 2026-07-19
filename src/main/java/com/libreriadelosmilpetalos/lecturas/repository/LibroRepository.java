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

    @EntityGraph(value = "etiquetas-de-libro", type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = """
    SELECT l
    FROM Libro l
    WHERE (:generos IS NULL OR l.genero IN :generos)
    AND (SELECT COUNT(e)
        FROM Etiqueta e
        WHERE e.libro.id = l.id
        AND e.descripcion IN :etiquetas
    ) >= :cantidad
    """,
    countQuery = """
    SELECT COUNT(1)
    FROM Libro l
    WHERE (:generos IS NULL OR l.genero IN :generos)
    AND (SELECT COUNT(e)
        FROM Etiqueta e
        WHERE e.libro.id = l.id
        AND e.descripcion IN :etiquetas
    ) >= :cantidad
    """)
    Page<Libro> findByGeneroAndEtiqueta(
        @Param("generos") List<Genero> generos,
        @Param("etiquetas") List<RepoEtiquetas> etiquetas,
        @Param("cantidad") Integer cantidad,
        Pageable pageable);
    
    @EntityGraph(value = "etiquetas-de-libro", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Libro> findByTituloIgnoreCase(String titulo);

    @EntityGraph(value = "etiquetas-de-libro", type = EntityGraph.EntityGraphType.FETCH)
    @Query(
    value = """
    SELECT l
    FROM Libro l
    WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :texto, '%'))
    """,
    countQuery = """
    SELECT COUNT(1)
    FROM Libro l
    WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :texto, '%'))
    """)
    Page<Libro> findWhenContainingText(
        @Param("texto") String texto,
        Pageable pageable);
}
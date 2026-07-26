package com.libreriadelosmilpetalos.lecturas.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.libreriadelosmilpetalos.lecturas.entity.Genero;
import com.libreriadelosmilpetalos.lecturas.entity.Libro;
import com.libreriadelosmilpetalos.lecturas.entity.RepoEtiquetas;

@DataJpaTest
public class LibroRepositoryTest {

    @Autowired
    private LibroRepository repo;

    // Declaraciones
    Pageable pageable;

    @BeforeEach
    void setUp()
    {
        pageable = PageRequest.of(0, 10, Sort.by("titulo").ascending());
    }

    // ======================================
    // TEST
    // ======================================

    @DisplayName("Busca libros que tengan cada género y etiqueta cuando hay")
    @Test
    void findByGeneroAndEtiqueta_GoodCase_Page() {

        List<Genero> generos = List.of(Genero.LITERARIO);
        List<RepoEtiquetas> etiquetas = List.of(RepoEtiquetas.NOVELA, RepoEtiquetas.NOVELA_BELICA);
        Integer cantidad = 2;

        Page<Libro> resultado = repo.findByGeneroAndEtiqueta(generos, etiquetas, cantidad, pageable);

        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());

        assertEquals("La Ladrona de Libros", resultado.getContent().get(1).getTitulo());
        assertThat(resultado.getContent().get(0).getEtiquetas())
            .isNotNull()
            .size().isEqualTo(3);
    }

    @DisplayName("Busca libros que coincidan con el género y etiqueta sin coincidencias")
    @Test
    void findByGeneroAndEtiqueta_NoneResult_VoidPage() {

        List<Genero> generos = List.of(Genero.NO_LITERARIO);
        List<RepoEtiquetas> etiquetas = List.of(RepoEtiquetas.NOVELA);
        Integer cantidad = 1;

        Page<Libro> resultado = repo.findByGeneroAndEtiqueta(generos, etiquetas, cantidad, pageable);

        assertNotNull(resultado);
        assertEquals(0, resultado.getTotalElements());
    }

    @DisplayName("Busca libros que tengan autores y títulos que coincidan con el texto cuando hay")
    @Test
    void findWhenContainingText_GoodCase_Page() {

        String texto = "la";

        Page<Libro> resultado = repo.findWhenContainingText(texto, pageable);

        assertNotNull(resultado);
        assertEquals(3, resultado.getTotalElements());

        assertEquals("El Señor de los Anillos: La Comunidad del Anillo", resultado.getContent().get(0).getTitulo());
        assertThat(resultado.getContent().get(0).getEtiquetas())
            .isNotNull()
            .size().isEqualTo(3);
    }

    @DisplayName("No hay libros que coincidan con la búsqueda")
    @Test
    void findWhenContainingText_NoneResult_VoidPage() {

        String texto = "Mitos";

        Page<Libro> resultado = repo.findWhenContainingText(texto, pageable);

        assertNotNull(resultado);
        assertEquals(0, resultado.getTotalElements());
    }
}

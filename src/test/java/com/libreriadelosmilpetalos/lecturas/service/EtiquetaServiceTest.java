package com.libreriadelosmilpetalos.lecturas.service;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.libreriadelosmilpetalos.lecturas.dto.EtiquetaDTO;
import com.libreriadelosmilpetalos.lecturas.entity.Etiqueta;
import com.libreriadelosmilpetalos.lecturas.entity.Libro;
import com.libreriadelosmilpetalos.lecturas.entity.RepoEtiquetas;
import com.libreriadelosmilpetalos.lecturas.repository.EtiquetaRepository;

@ExtendWith(MockitoExtension.class)
public class EtiquetaServiceTest {

    @Mock
    private EtiquetaRepository repo;

    @InjectMocks
    private EtiquetaService service;

    Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro();
        libro.setId(1L);
    }

    // ===================================
    // MÉTODOS DE AGREGAR
    // ===================================

    @DisplayName("Test de agregar etiqueta Happy Path")
    @Test
    void agregarEtiquetas_HappyPath() {

        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setDescripcion(RepoEtiquetas.NOVELA);
        etiqueta.setLibro(libro);

        List<Etiqueta> etiquetas = List.of(etiqueta);

        List<Etiqueta> guardadas = List.of(new Etiqueta());

        given(repo.saveAll(etiquetas))
            .willReturn(guardadas);

        List<EtiquetaDTO> resultado = service.agregarEtiquetas(etiquetas);

        assertThat(resultado)
            .isNotNull()
            .size().isEqualTo(1);

        verify(repo).saveAll(etiquetas);
    }

    @DisplayName("Test de agregar etiqueta excede el límite")
    @Test
    void agregarEtiquetas_ExcedeElLimite() {
        
        List<Etiqueta> etiquetas = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            etiquetas.add(new Etiqueta());
        }

        IllegalArgumentException resultado = assertThrows(IllegalArgumentException.class, () -> service.agregarEtiquetas(etiquetas));

        assertNotNull(resultado);
        assertEquals("Límite excedido. Máximo 10 etiquetas.", resultado.getMessage());
    }
}

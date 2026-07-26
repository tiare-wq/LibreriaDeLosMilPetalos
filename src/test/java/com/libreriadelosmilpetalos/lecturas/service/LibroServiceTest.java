package com.libreriadelosmilpetalos.lecturas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.libreriadelosmilpetalos.lecturas.dto.EtiquetaDTO;
import com.libreriadelosmilpetalos.lecturas.dto.LibroDTO;
import com.libreriadelosmilpetalos.lecturas.entity.Etiqueta;
import com.libreriadelosmilpetalos.lecturas.entity.Genero;
import com.libreriadelosmilpetalos.lecturas.entity.Libro;
import com.libreriadelosmilpetalos.lecturas.entity.RepoEtiquetas;
import com.libreriadelosmilpetalos.lecturas.repository.LibroRepository;

@ExtendWith(MockitoExtension.class)
public class LibroServiceTest {

    @InjectMocks
    private LibroService libroService;

    @Mock
    private LibroRepository repo;

    @Mock
    private EtiquetaService etiService;

    Libro entidad;
    LibroDTO dto;

    Etiqueta eti1;
    Etiqueta eti2;
    EtiquetaDTO etiDTO1;
    EtiquetaDTO etiDTO2;

    List<Etiqueta> etiquetas;
    List<EtiquetaDTO> etiquetasDTO;
    

    @BeforeEach
    void setUp() {
        entidad = new Libro();
        entidad.setId(1L);
        entidad.setTitulo("Libro");
        entidad.setAutor("Marcela Paz");
        entidad.setOpinion("Me encantó");
        entidad.setValoracion(5F);
        entidad.setGenero(Genero.LITERARIO);
        entidad.setFechaActualizacion(LocalDate.now());
        entidad.setFechaIngreso(LocalDate.now().minusDays(1L));

        dto = new LibroDTO();
        dto.setTitulo("Libro");
        dto.setAutor("Marcela Paz");
        dto.setOpinion("Me encantó");
        dto.setValoracion(5F);
        dto.setGenero(Genero.LITERARIO);
        dto.setFechaActualizacion(LocalDate.now());
        dto.setFechaIngreso(LocalDate.now().minusDays(1L));

        eti1 = new Etiqueta();
        eti1.setId(1L);
        eti1.setDescripcion(RepoEtiquetas.ADULTO_JOVEN);
        eti1.setLibro(entidad);

        eti2 = new Etiqueta();
        eti2.setId(1L);
        eti2.setDescripcion(RepoEtiquetas.AMISTAD);
        eti2.setLibro(entidad);

        etiDTO1 = new EtiquetaDTO();
        etiDTO1.setDescripcion(RepoEtiquetas.ADULTO_JOVEN);

        etiDTO2 = new EtiquetaDTO();
        etiDTO2.setDescripcion(RepoEtiquetas.AMISTAD);

        etiquetas = List.of(eti1, eti2);
        etiquetasDTO = List.of(etiDTO1, etiDTO2);

        entidad.setEtiquetas(etiquetas);
        dto.setEtiquetas(etiquetasDTO);
    }

    // ==========================================
    // MÉTODOS DE AGREGAR
    // ==========================================

    /* @Test
    void agregarLibro_DeberiaGuardarLibro() {

        when(repo.existsByTituloIgnoreCase(dto.getTitulo())).thenReturn(false);

        when(repo.save(any(Libro.class))).thenReturn(entidad);

        when(etiService.normalizarEtiquetas(eq(entidad), eq(etiquetasDTO))).thenReturn(etiquetas);

        when(etiService.agregarEtiquetas(etiquetas)).thenReturn(etiquetasDTO);

        LibroDTO resultado = libroService.agregarLibro(dto);

        assertNotNull(resultado);

        assertNull(resultado.getFechaActualizacion());
        assertEquals(LocalDate.now(), resultado.getFechaIngreso());
        assertEquals(2, resultado.getEtiquetas().size());
        assertEquals("AMISTAD", resultado.getEtiquetas().get(1).getDescripcion());

        verify(repo).save(any(Libro.class));
    } */

    @Test
    void agregarLibro_DeberiaGuardarLibro() {

        // Arrange
        dto.setEtiquetas(etiquetasDTO);

        when(repo.existsByTituloIgnoreCase(dto.getTitulo()))
                .thenReturn(false);

        when(repo.save(any(Libro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(etiService.normalizarEtiquetas(
                any(Libro.class),
                eq(etiquetasDTO)))
                .thenReturn(etiquetas);

        when(etiService.agregarEtiquetas(etiquetas))
                .thenReturn(etiquetasDTO);

        // Act
        LibroDTO resultado = libroService.agregarLibro(dto);

        // Assert
        assertNotNull(resultado);

        assertEquals(dto.getTitulo(), resultado.getTitulo());
        assertEquals(dto.getAutor(), resultado.getAutor());
        assertEquals(dto.getOpinion(), resultado.getOpinion());
        assertEquals(dto.getValoracion(), resultado.getValoracion());
        assertEquals(dto.getGenero(), resultado.getGenero());

        assertEquals(LocalDate.now(), resultado.getFechaIngreso());
        assertNull(resultado.getFechaActualizacion());

        assertEquals(2, resultado.getEtiquetas().size());
        assertEquals(RepoEtiquetas.ADULTO_JOVEN,
                resultado.getEtiquetas().get(0).getDescripcion());
        assertEquals(RepoEtiquetas.AMISTAD,
                resultado.getEtiquetas().get(1).getDescripcion());

        verify(repo).existsByTituloIgnoreCase(dto.getTitulo());
        verify(repo).save(any(Libro.class));
        verify(etiService).normalizarEtiquetas(any(Libro.class), eq(etiquetasDTO));
        verify(etiService).agregarEtiquetas(etiquetas);
    }
    // ==========================================
    // MÉTODOS DE BÚSQUEDA
    // ==========================================

    @Test
    void mostrarTodos_NormalCase() {

    }

    @Test
    void buscaLibroPorTitulo_HappyPath() {
        // WHEN
        // THEN
        // ASSERT
    }

    // ==========================================
    // MÉTODOS DE BÚSQUEDA PERSONALIZADA
    // ==========================================
}

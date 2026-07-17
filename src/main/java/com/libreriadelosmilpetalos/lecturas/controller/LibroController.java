package com.libreriadelosmilpetalos.lecturas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libreriadelosmilpetalos.lecturas.dto.LibroDTO;
import com.libreriadelosmilpetalos.lecturas.service.LibroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequiredArgsConstructor()
@RequestMapping("/api/v1/libros")
public class LibroController {

    private final LibroService service;

    @GetMapping
    public ResponseEntity<Page<LibroDTO>> mostrarTodosLibros(
        @PageableDefault(size = 10, sort = "titulo") @ParameterObject Pageable pageable) {
        
        Page<LibroDTO> resultado = service.mostrarTodos(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

    @GetMapping("/{titulo}")
    public ResponseEntity<LibroDTO> buscarPorTitulo(@PathVariable String titulo) {
        LibroDTO resultado = service.buscarPorTitulo(titulo);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }
    
    @PostMapping
    public ResponseEntity<LibroDTO> agregarLibro(@Valid @RequestBody LibroDTO dto) {
        LibroDTO resultado = service.agregarLibro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @PutMapping
    public ResponseEntity<LibroDTO> actualizarLibro(@Valid @RequestBody LibroDTO dto) {
        LibroDTO resultado = service.actualizarLibro(dto);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

    @DeleteMapping("/{titulo}")
    public ResponseEntity<?> eliminarLibro(@PathVariable String titulo) {
        service.eliminarLibroPorTitulo(titulo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}

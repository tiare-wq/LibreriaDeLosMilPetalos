package com.libreriadelosmilpetalos.lecturas.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@NamedEntityGraph(
    name = "etiquetas-relacionadas",
    attributeNodes = {@NamedAttributeNode("etiqueta")})
@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long id;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    private String opinion;

    @Column(nullable = false)
    private Float valoracion;

    @Column(nullable = false)
    private Genero genero;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaActualizacion;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @OneToMany(mappedBy = "libro")
    private List<Etiqueta> etiquetas;
}

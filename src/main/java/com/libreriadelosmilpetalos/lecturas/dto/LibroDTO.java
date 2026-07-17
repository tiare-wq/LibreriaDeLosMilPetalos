package com.libreriadelosmilpetalos.lecturas.dto;

import java.time.LocalDate;
import java.util.List;

import com.libreriadelosmilpetalos.lecturas.entity.Genero;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO {

    @NotBlank(message = "Escriba un título")
    @Size(max = 120, message = "No puede exceder los 120 caracteres")
    private String titulo;

    @NotBlank(message = "Ingrese un autor")
    @Size(max = 60, message = "No puede exceder los 60 caracteres")
    private String autor;

    @Size(min = 10, max = 500, message = "Debe tener entre 10 y 500 caracteres")
    private String opinion;

    @NotNull(message = "Seleccione una valoración")
    @Size(min = 1, max = 5, message = "Fuera del rango permitido (1.0 - 5.0)")
    private Float valoracion;

    @NotNull(message = "Ingrese un género")
    private Genero genero;

    @PastOrPresent(message = "La fecha no puede ser posterior a la actual")
    private LocalDate fechaActualizacion;

    @PastOrPresent(message = "La fecha no puede ser posterior a la actual")
    private LocalDate fechaIngreso;

    private List<EtiquetaDTO> etiquetas;
}

package com.libreriadelosmilpetalos.lecturas.dto;

import java.util.List;

import com.libreriadelosmilpetalos.lecturas.entity.Genero;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneroEtiquetaDTO {

    @Valid
    List<Genero> generos;

    @Valid
    List<EtiquetaDTO> etiquetas;
}

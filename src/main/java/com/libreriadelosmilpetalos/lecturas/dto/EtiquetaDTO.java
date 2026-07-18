package com.libreriadelosmilpetalos.lecturas.dto;

import com.libreriadelosmilpetalos.lecturas.entity.RepoEtiquetas;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtiquetaDTO {

    @NotNull(message = "No puede ser null")
    private RepoEtiquetas descripcion;
}

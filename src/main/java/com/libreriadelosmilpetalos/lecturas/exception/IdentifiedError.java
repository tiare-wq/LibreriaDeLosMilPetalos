package com.libreriadelosmilpetalos.lecturas.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentifiedError {

    private String attribute;

    private String message;
}

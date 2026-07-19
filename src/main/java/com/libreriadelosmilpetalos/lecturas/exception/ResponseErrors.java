package com.libreriadelosmilpetalos.lecturas.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrors {

    private int status;

    private String error;

    private String message;

    private List<IdentifiedError> errors;
}

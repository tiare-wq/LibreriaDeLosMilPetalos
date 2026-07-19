package com.libreriadelosmilpetalos.lecturas.exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {

    private int status;

    private String error;

    private String message;

    private List<IdentifiedError> errors;

    public ResponseError(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }
}

package com.libreriadelosmilpetalos.lecturas.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> resourceNotFoundExceptionHandler (ResourceNotFoundException ex) {
        ResponseError error = new ResponseError(404,
            "NOT FOUND",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseError> resourceAlreadyExistsExceptionHandler (ResourceAlreadyExistsException ex) {
        ResponseError error = new ResponseError(409,
            "CONFLICT",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> illegalArgumentExceptionHandler (IllegalArgumentException ex) {
        ResponseError error = new ResponseError(400,
            "BAD REQUEST",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrors> MethodArgumentNotValidExceptionHandler (MethodArgumentNotValidException ex) {
        List<IdentifiedError> errores = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(e -> new IdentifiedError(e.getField(), e.getDefaultMessage()))
            .toList();
        
        ResponseErrors error = new ResponseErrors(400,
            "BAD REQUEST",
            "Uno o más datos inválidos",
            errores
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> exceptionHandler (Exception ex) {
        ResponseError error = new ResponseError(500,
            "INTERNAL SERVER ERROR",
            ex.getMessage());
            // "Ha ocurrido un error del sistema. Si el problema persiste comuníquese con el administrador."
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

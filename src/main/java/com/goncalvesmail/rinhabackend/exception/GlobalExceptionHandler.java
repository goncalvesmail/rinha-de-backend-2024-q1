package com.goncalvesmail.rinhabackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> trataExcecoesNegocio(NegocioException e) {
        return ResponseEntity.unprocessableEntity().body(e.getMessage());
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> trataExcecoesClienteNaoEncontrado(ClienteNaoEncontradoException e) {
        return ResponseEntity.notFound().build();
    }
}

package br.com.fiap.tc.sistema.parquimetro.api.exception.handler;

import br.com.fiap.tc.sistema.parquimetro.api.exception.ErroCustomizado;
import br.com.fiap.tc.sistema.parquimetro.api.exception.RecursoNotFoundException;
import br.com.fiap.tc.sistema.parquimetro.api.exception.ValidacaoErro;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNotFoundException.class)
    public ResponseEntity<ErroCustomizado> handleRecursoNotFoundException(RecursoNotFoundException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroCustomizado erro = new ErroCustomizado(
            Instant.now(),
            status.value(),
            e.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroCustomizado> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(
                Instant.now(),
                status.value(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroCustomizado> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidacaoErro erro = new ValidacaoErro(
            Instant.now(),
            status.value(),
            "Dado inválido",
            request.getRequestURI()
        );
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            erro.addErros(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroCustomizado> httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroCustomizado erro = new ErroCustomizado(
            Instant.now(),
            status.value(),
            e.getMessage(),
            request.getRequestURI()
        );
        return ResponseEntity.status(status).body(erro);
    }
}

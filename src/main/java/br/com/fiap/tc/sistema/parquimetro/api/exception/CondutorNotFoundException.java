package br.com.fiap.tc.sistema.parquimetro.api.exception;

public class CondutorNotFoundException extends RuntimeException{

    public CondutorNotFoundException(String mensagem) {
        super(mensagem);
    }
}

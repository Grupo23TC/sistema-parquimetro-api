package br.com.fiap.tc.sistema.parquimetro.api.exception;

public class CondutorNotFounException extends RuntimeException{

    public CondutorNotFounException(String mensagem) {
        super(mensagem);
    }
}

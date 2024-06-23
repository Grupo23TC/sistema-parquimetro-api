package br.com.fiap.tc.sistema.parquimetro.api.exceptionhandler.exception;

public class CondutorNotFounException extends RuntimeException{

    public CondutorNotFounException(String mensagem) {
        super(mensagem);
    }
}

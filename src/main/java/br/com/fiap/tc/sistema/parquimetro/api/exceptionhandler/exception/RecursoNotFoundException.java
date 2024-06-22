package br.com.fiap.tc.sistema.parquimetro.api.exceptionhandler.exception;

public class RecursoNotFoundException extends RuntimeException {
  public RecursoNotFoundException(String mensagem) {
    super(mensagem);
  }
}

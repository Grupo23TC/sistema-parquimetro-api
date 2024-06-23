package br.com.fiap.tc.sistema.parquimetro.api.exception;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidacaoErro extends ErroCustomizado{
  private final List<CampoErro> campoErros = new ArrayList<CampoErro>();

  public ValidacaoErro(Instant horario, Integer status, String erro, String rota) {
    super(horario, status, erro, rota);
  }

  public void addErros(String campoNome, String mensagem) {
    campoErros.removeIf(erro -> erro.getNomeCampo().equals(campoNome));
    campoErros.add(new CampoErro(campoNome, mensagem));
  }
}

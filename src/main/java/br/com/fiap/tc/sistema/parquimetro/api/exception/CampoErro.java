package br.com.fiap.tc.sistema.parquimetro.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CampoErro {
  private final String nomeCampo;
  private final String mensagem;

}

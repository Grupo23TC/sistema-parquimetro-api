package br.com.fiap.tc.sistema.parquimetro.api.exceptionhandler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ErroCustomizado {
  private final Instant horario;
  private final Integer status;
  private final String erro;
  private final String rota;

}

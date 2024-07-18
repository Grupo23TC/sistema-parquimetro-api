package br.com.fiap.tc.sistema.parquimetro.api.model.dto;

import br.com.fiap.tc.sistema.parquimetro.api.model.Locacao;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusReciboEnum;

import java.time.LocalDateTime;

public record ReciboDTO(
        String id,
        StatusReciboEnum status,
        Locacao locacao,
        String tempoEstacionado,
        Double tarifa,
        FormaPagamentoEnum formaPagamento,
        Double valorTotal
) {
}
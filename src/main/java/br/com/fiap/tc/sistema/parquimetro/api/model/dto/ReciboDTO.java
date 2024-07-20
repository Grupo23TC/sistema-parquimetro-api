package br.com.fiap.tc.sistema.parquimetro.api.model.dto;

import br.com.fiap.tc.sistema.parquimetro.api.model.Locacao;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusReciboEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ReciboDTO(
    String id,
    @Schema(example = "FINALIZADO", requiredMode = Schema.RequiredMode.REQUIRED)
    StatusReciboEnum status,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Locacao locacao,
    @Schema(example = "5h", requiredMode = Schema.RequiredMode.REQUIRED)
    String tempoEstacionado,
    @Schema(description = "Tarifa a ser paga por hora pelo condutor", example = "20.0", requiredMode = Schema.RequiredMode.REQUIRED)
    Double tarifa,
    @Schema(example = "PIX", requiredMode = Schema.RequiredMode.REQUIRED)
    FormaPagamentoEnum formaPagamento,
    @Schema(description = "Valor total a ser pago por hora pelo condutor", example = "200.0", requiredMode = Schema.RequiredMode.REQUIRED)
    Double valorTotal
) {
}

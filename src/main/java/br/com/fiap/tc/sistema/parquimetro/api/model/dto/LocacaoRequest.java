package br.com.fiap.tc.sistema.parquimetro.api.model.dto;

import br.com.fiap.tc.sistema.parquimetro.api.model.Periodo;
import io.swagger.v3.oas.annotations.media.Schema;

public record LocacaoRequest(
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    String idCondutor,
    @Schema(example = "AAA0A00", requiredMode = Schema.RequiredMode.REQUIRED)
    String placaVeiculo,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    EnderecoDTO enderecoVaga,
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    Periodo periodo
) {
}

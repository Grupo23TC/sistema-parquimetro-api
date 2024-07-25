package br.com.fiap.tc.sistema.parquimetro.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record EnderecoDTO (
        @Schema(example = "Rua XPTO", requiredMode = Schema.RequiredMode.REQUIRED)
        String rua,
        @Schema(example = "Bl9 apto99")
        String complemento,
        @Schema(example = "São Paulo", requiredMode = Schema.RequiredMode.REQUIRED)
        String cidade,
        @Schema(example = "SP", requiredMode = Schema.RequiredMode.REQUIRED)
        String estado,
        @Schema(example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED)
        String cep
){
}

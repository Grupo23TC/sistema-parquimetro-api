package br.com.fiap.tc.sistema.parquimetro.api.model.dto;

import br.com.fiap.tc.sistema.parquimetro.api.model.Periodo;

public record LocacaoDTO(
    String idCondutor,
    String placaVeiculo,
    EnderecoDTO enderecoVaga,
    Periodo periodo
) {
}

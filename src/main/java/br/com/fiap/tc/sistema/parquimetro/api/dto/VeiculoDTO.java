package br.com.fiap.tc.sistema.parquimetro.api.dto;

import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;

import java.util.List;

public record VeiculoDTO(

        String id,

        String placa,

        String tipo,

        List<Recibo> recibos
){
}

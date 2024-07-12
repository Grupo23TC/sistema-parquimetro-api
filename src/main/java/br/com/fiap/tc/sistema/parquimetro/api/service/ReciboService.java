package br.com.fiap.tc.sistema.parquimetro.api.service;


import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoDTO;

public interface ReciboService {

    Recibo iniciarLocacao(LocacaoDTO locacaoDTO);

}

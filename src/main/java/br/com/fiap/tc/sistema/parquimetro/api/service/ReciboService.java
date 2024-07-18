package br.com.fiap.tc.sistema.parquimetro.api.service;


import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoRequest;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;

public interface ReciboService {

    ReciboDTO iniciarLocacao(LocacaoRequest locacaoRequest);

}

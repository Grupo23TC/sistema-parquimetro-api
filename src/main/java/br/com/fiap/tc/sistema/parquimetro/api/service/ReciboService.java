package br.com.fiap.tc.sistema.parquimetro.api.service;


import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoRequest;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;

import java.util.List;

public interface ReciboService {
    List<ReciboDTO> buscarRecibos();

    public ReciboDTO iniciarLocacao(LocacaoRequest locacaoRequest);

    public ReciboDTO finalizarLocacao(String reciboId);

    public ReciboDTO buscarReciboPorId(String reciboId);

    public void atualizar(Recibo updateRecibo);

    void finalizarReciboFixoScheduler();

}

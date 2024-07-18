package br.com.fiap.tc.sistema.parquimetro.api.service;


import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoRequest;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;

public interface ReciboService {

    public Recibo iniciarLocacao(LocacaoRequest locacaoRequest);

    public void finalizarLocacao(String reciboId);

    public ReciboDTO buscarReciboPorId(String reciboId);

    public void atualizar(Recibo updateRecibo);

}

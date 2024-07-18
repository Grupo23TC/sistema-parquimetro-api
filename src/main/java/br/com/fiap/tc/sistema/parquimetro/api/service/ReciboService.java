package br.com.fiap.tc.sistema.parquimetro.api.service;


import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.CondutorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;

public interface ReciboService {

    public Recibo iniciarLocacao(LocacaoDTO locacaoDTO);

    public void finalizarLocacao(String reciboId);

    public ReciboDTO buscarReciboPorId(String reciboId);

    public void atualizar(Recibo updateRecibo);

}

package br.com.fiap.tc.sistema.parquimetro.api.service;

import br.com.fiap.tc.sistema.parquimetro.api.model.dto.CondutorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.Condutor;

import java.util.List;


public interface CondutorService {

    public void validarCondutor(Condutor condutor) throws RuntimeException;

    public List<CondutorDTO> listaCondutor();

    public CondutorDTO buscarCondutorPorId(String condutorId);

    public CondutorDTO criarCondutor(CondutorDTO condutorDTO);

    public void atualizarFormaPagamento(String id, FormaPagamentoEnum formaPagamento);

    public CondutorDTO atualizarCondutor(String condutorId, CondutorDTO condutorDTO);

    public void deletarCondutor(String condutorId);
}


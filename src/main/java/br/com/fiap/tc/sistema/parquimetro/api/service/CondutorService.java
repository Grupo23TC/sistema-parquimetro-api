package br.com.fiap.tc.sistema.parquimetro.api.service;

import br.com.fiap.tc.sistema.parquimetro.api.dto.CondutorDto;
import br.com.fiap.tc.sistema.parquimetro.api.model.Condutor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CondutorService {

    void validarCondutor(Condutor condutor) throws RuntimeException;

    List<CondutorDto> listaCondutor();

    CondutorDto buscarCondutorPorId(String condutorId);

    CondutorDto criarCondutor(CondutorDto condutorDto);

    CondutorDto atualizarCondutor(String condutorId, CondutorDto condutorDto);

    void deletarCondutor(String condutorId);
}



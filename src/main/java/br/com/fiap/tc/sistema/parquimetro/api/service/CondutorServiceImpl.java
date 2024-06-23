package br.com.fiap.tc.sistema.parquimetro.api.service;

import br.com.fiap.tc.sistema.parquimetro.api.dto.CondutorDto;
import br.com.fiap.tc.sistema.parquimetro.api.exception.CondutorNotFounException;
import br.com.fiap.tc.sistema.parquimetro.api.model.Condutor;
import br.com.fiap.tc.sistema.parquimetro.api.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondutorServiceImpl implements CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    public CondutorServiceImpl(CondutorRepository condutorRepository) {
        this.condutorRepository = condutorRepository;
    }

    @Override
    public void validarCondutor(Condutor condutor) {
        Condutor condutorExistente = condutorRepository.findById(condutor.getId()).orElseThrow(() -> new CondutorNotFounException("Condutor não encontrado"));
    }
    @Override
    @Transactional(readOnly = true)
    public List<CondutorDto> listaCondutor() {
        List<Condutor> condutores = condutorRepository.findAll();
        return condutores.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CondutorDto buscarCondutorPorId(String condutorId) {
        Condutor condutor = condutorRepository.findById(condutorId)
                .orElseThrow(() -> new CondutorNotFounException(condutorId));
        return toDTO(condutor);
    }

    @Override
    public CondutorDto criarCondutor(CondutorDto condutorDto) {
        Condutor condutor = new Condutor();
        condutor.setNome(condutorDto.nome());
        condutor.setTelefone(condutorDto.telefone());
        condutor.setEmail(condutorDto.email());
        condutor.setCpf(condutorDto.cpf());
        condutor.setFormaPagamento(condutorDto.formaPagamento());

        Condutor saveCondutor = condutorRepository.save(condutor);
        return toDTO(saveCondutor);
    }

    @Override
    public CondutorDto atualizarCondutor(String condutorId, CondutorDto condutorDto) {
        Condutor condutor = condutorRepository.findById(condutorId)
                .orElseThrow(() -> new CondutorNotFounException(condutorId));

        condutor.setNome(condutorDto.nome());
        condutor.setTelefone(condutorDto.telefone());
        condutor.setEmail(condutorDto.email());
        condutor.setCpf(condutorDto.cpf());
        condutor.setFormaPagamento(condutorDto.formaPagamento());


        Condutor updatedCondutor = condutorRepository.save(condutor);
        return toDTO(updatedCondutor);
    }

    @Override
    public void deletarCondutor(String condutorId) {
        condutorRepository.deleteById(condutorId);
    }

    //TODO: Implementar o método toDTO
   private CondutorDto toDTO(Condutor condutor) {
        return new CondutorDto(
                condutor.getId(),
                condutor.getNome(),
                condutor.getTelefone(),
                condutor.getEmail(),
                condutor.getCpf(),
                condutor.getVeiculos(),
                condutor.getFormaPagamento()
        );
}

}

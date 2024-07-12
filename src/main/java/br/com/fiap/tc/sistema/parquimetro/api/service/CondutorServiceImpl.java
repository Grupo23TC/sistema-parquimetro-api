package br.com.fiap.tc.sistema.parquimetro.api.service;

import br.com.fiap.tc.sistema.parquimetro.api.exception.CondutorNotFoundException;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.CondutorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.Condutor;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondutorServiceImpl implements CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void validarCondutor(Condutor condutor) {
        Condutor condutorExistente = condutorRepository.findById(condutor.getId()).orElseThrow(() -> new CondutorNotFoundException("Condutor não encontrado"));
    }
    @Override
    @Transactional(readOnly = true)
    public List<CondutorDTO> listaCondutor() {
        List<Condutor> condutores = condutorRepository.findAll();
        return condutores.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CondutorDTO buscarCondutorPorId(String condutorId) {
        Condutor condutor = condutorRepository.findById(condutorId)
                .orElseThrow(() -> new CondutorNotFoundException(condutorId));
        return toDTO(condutor);
    }

    @Override
    public CondutorDTO criarCondutor(CondutorDTO condutorDTO) {
        Condutor condutor = new Condutor();

        condutor.setNome(condutorDTO.nome());
        condutor.setTelefone(condutorDTO.telefone());
        condutor.setEmail(condutorDTO.email());
        condutor.setCPF(condutorDTO.CPF());
        condutor.setEndereco(condutorDTO.endereco());
        condutor.setFormaPagamento(condutorDTO.formaPagamento());
        condutor.setVeiculos(condutorDTO.veiculos());

        Condutor saveCondutor = condutorRepository.save(condutor);
        return toDTO(saveCondutor);
    }

    @Override
    public void atualizarFormaPagamento(String id, FormaPagamentoEnum formaPagamento) {

        Query query = new Query(Criteria.where("_id").is(id));

        Update update = new Update().set("formaPagamento", formaPagamento);

        this.mongoTemplate.updateFirst(query, update, Condutor.class);
    }

    @Override
    public CondutorDTO atualizarCondutor(String condutorId, CondutorDTO condutorDTO) {
        Condutor condutor = condutorRepository.findById(condutorId)
                .orElseThrow(() -> new CondutorNotFoundException(condutorId));

        condutor.setNome(condutorDTO.nome());
        condutor.setTelefone(condutorDTO.telefone());
        condutor.setEmail(condutorDTO.email());
        condutor.setCPF(condutorDTO.CPF());
        condutor.setEndereco(condutorDTO.endereco());
        condutor.setFormaPagamento(condutorDTO.formaPagamento());


        Condutor updatedCondutor = condutorRepository.save(condutor);
        return toDTO(updatedCondutor);
    }

    @Override
    public void deletarCondutor(String condutorId) {
        condutorRepository.deleteById(condutorId);
    }
   private CondutorDTO toDTO(Condutor condutor) {
        return new CondutorDTO(
                condutor.getId(),
                condutor.getNome(),
                condutor.getTelefone(),
                condutor.getEmail(),
                condutor.getCPF(),
                condutor.getEndereco(),
                condutor.getVeiculos(),
                condutor.getFormaPagamento()
        );
    }

}

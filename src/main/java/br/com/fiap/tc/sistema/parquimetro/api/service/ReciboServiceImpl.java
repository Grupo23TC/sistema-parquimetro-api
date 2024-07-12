package br.com.fiap.tc.sistema.parquimetro.api.service;

import br.com.fiap.tc.sistema.parquimetro.api.exception.RecursoNotFoundException;
import br.com.fiap.tc.sistema.parquimetro.api.model.*;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.CondutorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusReciboEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.TipoPeriodoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

@Service
public class ReciboServiceImpl implements ReciboService {

    @Autowired
    private CondutorService condutorService;

    @Autowired
    private ReciboRepository reciboRepository;

    @Value("${parquimetro.tarifa.hora}")
    private Double tarifa;

    @Transactional
    @Override
    public Recibo iniciarLocacao(LocacaoDTO locacaoDTO) {

        // Aqui o CondutorService já valida se existe o condutor
        CondutorDTO condutorDTO = condutorService.buscarCondutorPorId(locacaoDTO.idCondutor());

        // Validar se o veiculo informado na locacao está registrado para o condutor
        if (condutorDTO.veiculos().stream().noneMatch(veiculo -> veiculo.getPlaca().equals(locacaoDTO.placaVeiculo()))) {
            throw new RecursoNotFoundException("Veículo informado não está cadastrado para o condutor.");
        }

        // Validações para periodo fixo
        if (locacaoDTO.periodo().getTipoPeriodo().equals(TipoPeriodoEnum.FIXO)) {

            // Validação duração informada
            if (locacaoDTO.periodo().getDuracao() == null || locacaoDTO.periodo().getDuracao() <= 0) {
                throw new IllegalArgumentException("Para períodos fixos a duração em horas deve ser informada.");
            }

            // Validação forma pagamento PIX
            if (!condutorDTO.formaPagamento().equals(FormaPagamentoEnum.PIX)) {
                throw new IllegalArgumentException("Somente aceitamos a forma de pagamento PIX para períodos fixos.");
            }

        }

        // Criação Locação + Recibo
        Veiculo veiculo = condutorDTO.veiculos().stream().filter(v -> v.getPlaca().equals(locacaoDTO.placaVeiculo())).findFirst().get();
        List<Veiculo> listVeiculos = new ArrayList<>();
        listVeiculos.add(veiculo);

        Condutor condutor = new Condutor();
        condutor.setId(condutorDTO.id());
        condutor.setNome(condutorDTO.nome());
        condutor.setVeiculos(listVeiculos);

        Vaga vaga = new Vaga(locacaoDTO.enderecoVaga());

        Locacao locacao = new Locacao();
        locacao.setCondutor(condutor);
        locacao.setVaga(vaga);
        locacao.setPeriodo(locacaoDTO.periodo());
        locacao.setInicio(LocalDateTime.now());

        if (locacao.getPeriodo().getTipoPeriodo().equals(TipoPeriodoEnum.FIXO)) {
            long duracao = Integer.toUnsignedLong(locacao.getPeriodo().getDuracao());
            locacao.setFim(locacao.getInicio().plus(duracao, HOURS));
        }

        Recibo recibo = new Recibo();
        recibo.setLocacao(locacao);
        recibo.setStatus(StatusReciboEnum.ABERTO);
        recibo.setTarifa(tarifa);
        recibo.setFormaPagamento(condutorDTO.formaPagamento());

        if (locacao.getPeriodo().getTipoPeriodo().equals(TipoPeriodoEnum.FIXO)) {
            recibo.setTempoEstacionado(locacao.getPeriodo().getDuracao() + "h");
            recibo.setValorTotal(tarifa * locacao.getPeriodo().getDuracao());
        }

        Recibo reciboSalvo = reciboRepository.save(recibo);

        return reciboSalvo;
    }

}



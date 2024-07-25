package br.com.fiap.tc.sistema.parquimetro.api.service;

import br.com.fiap.tc.sistema.parquimetro.api.exception.RecursoNotFoundException;
import br.com.fiap.tc.sistema.parquimetro.api.model.*;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.CondutorDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.LocacaoRequest;
import br.com.fiap.tc.sistema.parquimetro.api.model.dto.ReciboDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusReciboEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.TipoPeriodoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${parquimetro.tarifa.hora}")
    private Double tarifa;

    @Transactional
    @Override
    public ReciboDTO iniciarLocacao(LocacaoRequest locacaoRequest) {

        // Aqui o CondutorService já valida se existe o condutor
        CondutorDTO condutorDTO = condutorService.buscarCondutorPorId(locacaoRequest.idCondutor());

        // Validar se o veiculo informado na locacao está registrado para o condutor
        if (condutorDTO.veiculos().stream().noneMatch(veiculo -> veiculo.getPlaca().equals(locacaoRequest.placaVeiculo()))) {
            throw new RecursoNotFoundException("Veículo informado não está cadastrado para o condutor.");
        }

        // Validações para periodo fixo
        if (locacaoRequest.periodo().getTipoPeriodo().equals(TipoPeriodoEnum.FIXO)) {

            // Validação duração informada
            if (locacaoRequest.periodo().getDuracao() == null || locacaoRequest.periodo().getDuracao() <= 0) {
                throw new IllegalArgumentException("Para períodos fixos a duração em horas deve ser informada.");
            }

            // Validação forma pagamento PIX
            if (!condutorDTO.formaPagamento().equals(FormaPagamentoEnum.PIX)) {
                throw new IllegalArgumentException("Somente aceitamos a forma de pagamento PIX para períodos fixos.");
            }

        }

        // Criação Locação + Recibo
        Veiculo veiculo = condutorDTO.veiculos().stream().filter(v -> v.getPlaca().equals(locacaoRequest.placaVeiculo())).findFirst().get();
        List<Veiculo> listVeiculos = new ArrayList<>();
        listVeiculos.add(veiculo);

        Condutor condutor = criarCondutor(condutorDTO, listVeiculos);

        Vaga vaga = new Vaga(locacaoRequest.enderecoVaga());

        Locacao locacao = criarLocacao(condutor, vaga, locacaoRequest);

        if (locacao.getPeriodo().getTipoPeriodo().equals(TipoPeriodoEnum.FIXO)) {
            long duracao = Integer.toUnsignedLong(locacao.getPeriodo().getDuracao());
            locacao.setFim(locacao.getInicio().plus(duracao, HOURS));
        }

        Recibo recibo = criarRecibo(locacao, tarifa, condutor.getFormaPagamento());

        if (locacao.getPeriodo().getTipoPeriodo().equals(TipoPeriodoEnum.FIXO)) {
            recibo.setTempoEstacionado(locacao.getPeriodo().getDuracao() + "h");
            recibo.setValorTotal(tarifa * locacao.getPeriodo().getDuracao());
        }

        Recibo reciboSalvo = reciboRepository.save(recibo);

        return toDTO(reciboSalvo);
    }

    private Recibo criarRecibo(Locacao locacao, Double tarifa, FormaPagamentoEnum formaPagamento) {
        Recibo recibo = new Recibo();
        recibo.setLocacao(locacao);
        recibo.setStatus(StatusReciboEnum.ABERTO);
        recibo.setTarifa(tarifa);
        recibo.setFormaPagamento(formaPagamento);

        return recibo;
    }

    private Locacao criarLocacao(Condutor condutor, Vaga vaga, LocacaoRequest locacaoRequest) {
        Locacao locacao = new Locacao();
        locacao.setCondutor(condutor);
        locacao.setVaga(vaga);
        locacao.setPeriodo(locacaoRequest.periodo());
        locacao.setInicio(LocalDateTime.now());

        return locacao;
    }

    private Condutor criarCondutor(CondutorDTO condutorDTO, List<Veiculo> veiculos) {
        Condutor condutor = new Condutor();
        condutor.setId(condutorDTO.id());
        condutor.setNome(condutorDTO.nome());
        condutor.setVeiculos(veiculos);
        return condutor;
    }

    @Override
    @Transactional(readOnly = true)
    public ReciboDTO buscarReciboPorId(String reciboId) {
        Recibo recibo = reciboRepository.findById(reciboId)
                .orElseThrow(() -> new RecursoNotFoundException(reciboId));
        return toDTO(recibo);
    }

    @Override
    public void atualizar(Recibo updateRecibo) {
        this.reciboRepository.save(updateRecibo);
    }

    @Override
    public ReciboDTO finalizarLocacao(String reciboId) {
        // valida se existe o Recibo
        ReciboDTO recibo = buscarReciboPorId(reciboId);

        Recibo reciboIf = toRecibo(recibo);

        if(recibo.status().equals(StatusReciboEnum.ABERTO)
                && recibo.locacao().getPeriodo().getTipoPeriodo().equals(TipoPeriodoEnum.VARIAVEL)) {

            reciboIf.getLocacao().setFim(LocalDateTime.now());

            reciboIf.setStatus(StatusReciboEnum.FINALIZADO);
            reciboIf.setValorTotal(tarifa * 2); //TODO implementar calculo de hora aqui
        } //TODO caso o recibo já estiver FINALIZADO lançar exception
        atualizar(reciboIf);

        return toDTO(reciboIf);
    }

    private Recibo toRecibo(ReciboDTO reciboDTO) {
        return Recibo.builder()
                .id(reciboDTO.id())
                .status(reciboDTO.status())
                .locacao(reciboDTO.locacao())
                .tempoEstacionado(reciboDTO.tempoEstacionado())
                .tarifa(reciboDTO.tarifa())
                .formaPagamento(reciboDTO.formaPagamento())
                .valorTotal(reciboDTO.valorTotal())
                .build();
    }

    private ReciboDTO toDTO(Recibo recibo) {
        return new ReciboDTO(
                recibo.getId(),
                recibo.getStatus(),
                recibo.getLocacao(),
                recibo.getTempoEstacionado(),
                recibo.getTarifa(),
                recibo.getFormaPagamento(),
                recibo.getValorTotal()
        );
    }

    // Execução a cada 5min
    @Scheduled(cron = "0 */5 * ? * *")
    @Override
    public void finalizarReciboFixoScheduler() {

        // Consultar no banco todos recibos status ABERTO e locacao.periodo.tipoPeriodo = FIXO
        // e que estejam tenho data fim anterior a data de agora
        Query query = new Query(Criteria
                .where("status").is(StatusReciboEnum.ABERTO)
                .and("locacao.periodo.tipoPeriodo").is(TipoPeriodoEnum.FIXO)
                .and("locacao.fim")
                .lte(LocalDateTime.now()));

        List<Recibo> reciboList = mongoTemplate.find(query, Recibo.class);

        for (Recibo recibo : reciboList) {
            recibo.setStatus(StatusReciboEnum.FINALIZADO);
            atualizar(recibo);
            System.out.println("Condutor: " + recibo.getLocacao().getCondutor().getNome() +
                    " sua locação para o veículo de placa: " + recibo.getLocacao().getCondutor().getVeiculos().get(0).getPlaca() +
                    " foi automaticamente finalizada.");
        }
    }
}



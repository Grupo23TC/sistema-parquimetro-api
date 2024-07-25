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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
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


    private Recibo criarRecibo(Locacao locacao, Double tarifa, FormaPagamentoEnum formaPagamento) {
        Recibo recibo = new Recibo();
        recibo.setLocacao(locacao);
        recibo.setStatus(StatusReciboEnum.ABERTO);
        recibo.setTarifa(tarifa);
        recibo.setFormaPagamento(formaPagamento);

        return recibo;
    }

    @Transactional
    @Override
    public ReciboDTO iniciarLocacao(LocacaoRequest locacaoRequest) {
        // Valida se o veículo informado está registrado para o condutor especificado na requisição.
        CondutorDTO condutorDTO = validarCondutorEVeiculo(locacaoRequest);
        // Prepara a locação criando uma nova instância de Locacao com os dados fornecidos.
        Locacao locacao = prepararLocacao(condutorDTO, locacaoRequest);
        // Cria um novo recibo para a locação com a tarifa e forma de pagamento especificadas.
        Recibo recibo = criarRecibo(locacao, tarifa, condutorDTO.formaPagamento());
        // Salva o recibo no repositório e retorna o recibo salvo.
        Recibo reciboSalvo = reciboRepository.save(recibo);
        // Converte o recibo salvo para DTO e o retorna.
        return toDTO(reciboSalvo);
    }

    private CondutorDTO validarCondutorEVeiculo(LocacaoRequest locacaoRequest) {
        // Busca o condutor pelo ID fornecido na requisição.
        CondutorDTO condutorDTO = condutorService.buscarCondutorPorId(locacaoRequest.idCondutor());
        // Verifica se o veículo especificado está registrado para o condutor.
        boolean veiculoRegistrado = condutorDTO.veiculos().stream()
                .anyMatch(veiculo -> veiculo.getPlaca().equals(locacaoRequest.placaVeiculo()));
        // Se o veículo não estiver registrado, lança uma exceção.
        if (!veiculoRegistrado) {
            throw new RecursoNotFoundException("Veículo informado não está cadastrado para o condutor.");
        }
        // Retorna o DTO do condutor se o veículo estiver registrado.
        return condutorDTO;
    }

    private Locacao prepararLocacao(CondutorDTO condutorDTO, LocacaoRequest locacaoRequest) {
        // Filtra e encontra o veículo especificado na requisição dentre os veículos do condutor.
        Veiculo veiculo = condutorDTO.veiculos().stream()
                .filter(v -> v.getPlaca().equals(locacaoRequest.placaVeiculo()))
                .findFirst()
                .orElseThrow(() -> new RecursoNotFoundException("Veículo não encontrado."));
        // Cria uma lista de veículos e adiciona o veículo encontrado a ela.
        List<Veiculo> listVeiculos = new ArrayList<>();
        listVeiculos.add(veiculo);
        // Cria um novo condutor com os dados fornecidos e a lista de veículos.
        Condutor condutor = criarCondutor(condutorDTO, listVeiculos);
        // Cria uma nova vaga com o endereço fornecido na requisição.
        Vaga vaga = new Vaga(locacaoRequest.enderecoVaga());
        // Retorna uma nova locação criada com o condutor, a vaga e os dados da requisição.
        return criarLocacao(condutor, vaga, locacaoRequest);
    }

    private Locacao criarLocacao(Condutor condutor, Vaga vaga, LocacaoRequest locacaoRequest) {
        Locacao locacao = new Locacao();
        locacao.setCondutor(condutor);
        locacao.setVaga(vaga);
        locacao.setPeriodo(locacaoRequest.periodo());
        locacao.setInicio(LocalDateTime.now());
        if (locacao.getPeriodo().getTipoPeriodo().equals(TipoPeriodoEnum.VARIAVEL) &&
                locacao.getPeriodo().getDuracao() != null) {
            locacao.getPeriodo().setDuracao(null);
        }
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

    @Transactional
    @Override
    public void finalizarLocacao(String reciboId) {
        // Busca o recibo pelo ID fornecido.
        ReciboDTO recibo = buscarReciboPorId(reciboId);
        // Valida se o status do recibo permite finalização.
        validarStatusRecibo(recibo);
        // Atualiza o status do recibo para finalizado e calcula o valor total.
        atualizarStatusEValorTotal(recibo);
    }

    private void validarStatusRecibo(ReciboDTO recibo) {
        // Se o recibo já estiver finalizado, lança uma exceção.
        if (recibo.status().equals(StatusReciboEnum.FINALIZADO)) {
            throw new IllegalStateException("Recibo já está finalizado.");
        }
    }

    private void atualizarStatusEValorTotal(ReciboDTO reciboDTO) {
        // Converte o DTO para a entidade Recibo.
        Recibo recibo = toRecibo(reciboDTO);
        // Verifica se o recibo está aberto e se o tipo de período é variável.
        if (reciboDTO.status().equals(StatusReciboEnum.ABERTO)
                && reciboDTO.locacao().getPeriodo().getTipoPeriodo().equals(TipoPeriodoEnum.VARIAVEL)) {
            // Define o momento de finalização da locação.
            recibo.getLocacao().setFim(LocalDateTime.now());
            // Atualiza o status do recibo para finalizado.
            recibo.setStatus(StatusReciboEnum.FINALIZADO);
            // Calcula e define o valor total com base no tempo de estacionamento.
            recibo.setValorTotal(calcularValorTotal(recibo));
        }
        // Persiste as alterações no recibo.
        atualizar(recibo);
    }

    private Double calcularValorTotal(Recibo recibo) {
        long minutosEstacionado = Duration.between(
                recibo.getLocacao().getInicio(),
                recibo.getLocacao().getFim()
        ).toMinutes();

        return tarifa * Math.ceil(minutosEstacionado / 60.0);
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
}

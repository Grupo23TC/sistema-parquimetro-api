package br.com.fiap.tc.sistema.parquimetro.api.service;

import br.com.fiap.tc.sistema.parquimetro.api.model.Recibo;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusReciboEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.TipoPeriodoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AlertaServiceImpl implements AlertaService {

    private static final DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private MongoTemplate mongoTemplate;

    // Execução a cada 5min
    @Scheduled(cron = "0 */5 * ? * *")
    @Override
    public void alertaPeriodoFixo() {
        // Consultar no banco todos recibos status ABERTO e locacao.periodo.tipoPeriodo = FIXO
        // e que estejam no range do tempo de agora até agora +15 Min
        Query query = new Query(Criteria
                .where("status").is(StatusReciboEnum.ABERTO)
                .and("locacao.periodo.tipoPeriodo").is(TipoPeriodoEnum.FIXO)
                .and("locacao.fim")
                .gte(LocalDateTime.now())
                .lte(LocalDateTime.now().plus(15, ChronoUnit.MINUTES)));

        List<Recibo> reciboList = mongoTemplate.find(query, Recibo.class);

        for (Recibo recibo : reciboList) {
            System.out.println("Condutor: " + recibo.getLocacao().getCondutor().getNome() +
                    " sua locação para o veículo de placa: " + recibo.getLocacao().getCondutor().getVeiculos().get(0).getPlaca() +
                    " vai encerrar ás: " + recibo.getLocacao().getFim().format(formatter) +
                            " por favor retirar seu veículo.");
        }
    }

    // Execução a cada minuto
    @Scheduled(cron = "0 * * ? * *")
    public void alertaPeriodoVariavel() {
        Query query = new Query(Criteria
                .where("status").is(StatusReciboEnum.ABERTO)
                .and("locacao.periodo.tipoPeriodo").is(TipoPeriodoEnum.VARIAVEL));

        List<Recibo> reciboList = mongoTemplate.find(query, Recibo.class);

        // Calcular minuto da hora de agora
        int minutoAux = LocalDateTime.now().getMinute();

        // Filtramos a lista de recibos de periodo variavel em aberto,
        // no qual o minuto da data de inicio +15 min seja = ao minuto de agora
        // assim o alarme que roda a de minuto em minuto vai notificar apenas 1 vez o condutor a cada hora
        reciboList.stream()
                .filter(recibo ->
                        recibo.getLocacao().getInicio().plus(15, ChronoUnit.MINUTES).getMinute() == (minutoAux))
                .map(recibo -> "Condutor: " + recibo.getLocacao().getCondutor().getNome() +
                        " sua locação para o veículo de placa: " + recibo.getLocacao().getCondutor().getVeiculos().get(0).getPlaca() +
                        " vai completar mais 1 hora dentro de 15 minutos!")
                .forEach(System.out::println);
    }
}

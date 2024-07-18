package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusReciboEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "recibo")
public class Recibo {
    @Id
    private String id;
    private StatusReciboEnum status;
    private Locacao locacao;
    private String tempoEstacionado;
    private Double tarifa;
    private FormaPagamentoEnum formaPagamento;
    private Double valorTotal;
}

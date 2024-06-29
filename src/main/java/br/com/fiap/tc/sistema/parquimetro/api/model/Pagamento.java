package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusPagamentoEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pagamento")
public class Pagamento {
    @Id
    private String id;
    private BigDecimal valor;
    private String metodoPagamento;
    private StatusPagamentoEnum status;

}

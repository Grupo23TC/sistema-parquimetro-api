package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.StatusReciboEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Status do recibo do condutor", example = "FINALIZADO", requiredMode = Schema.RequiredMode.REQUIRED)
    private StatusReciboEnum status;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Locacao locacao;
    @Schema(example = "5h", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tempoEstacionado;
    @Schema(description = "Valor da tarifa a ser paga por hora", example = "20.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double tarifa;
    @Schema(description = "Meio pelo qual ocorreu o pagamento", example = "20.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private FormaPagamentoEnum formaPagamento;
    @Schema(description = "Valor total a ser pago pelo condutor", example = "200.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double valorTotal;
}

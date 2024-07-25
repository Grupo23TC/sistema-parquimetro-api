package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.model.enums.TipoPeriodoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Periodo {
    @Schema(example = "FIXO", requiredMode = Schema.RequiredMode.REQUIRED)
    private TipoPeriodoEnum tipoPeriodo;
    @Schema(example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer duracao;

}

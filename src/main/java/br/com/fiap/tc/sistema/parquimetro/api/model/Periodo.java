package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.model.enums.TipoPeriodoEnum;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Periodo {

    private TipoPeriodoEnum tipoPeriodo;
    private Integer duracao;

}

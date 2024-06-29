package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.model.enums.TipoPeriodoEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "periodo")
public class Periodo {
    @Id
    private String id;
    private TipoPeriodoEnum tipoPeriodoEnum;
    private Integer duracao;
}

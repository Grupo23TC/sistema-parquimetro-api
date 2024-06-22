package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.enums.TipoPeriodoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Periodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPeriodoEnum tipoPeriodoEnum;

    private int duracao;
}

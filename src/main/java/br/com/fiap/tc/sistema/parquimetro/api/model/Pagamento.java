package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.enums.StatusPagamentoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    private String metodoPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamentoEnum status;

}

package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.enums.FormaPagamentoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private int telefone;

    @NonNull
    private String email;

    @NonNull
    private String cpf;

    @NonNull
    @OneToMany(mappedBy = "condutor")
    private List<Condutor_Veiculo> veiculosList;

    @NonNull
    private FormaPagamentoEnum formaPagamento;
}

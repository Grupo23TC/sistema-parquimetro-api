package br.com.fiap.tc.sistema.parquimetro.api.model;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Condutor_Veiculo {

    private long id;

    @ManyToOne
    @JoinColumn (name = "id_condutor", referencedColumnName = "id")
    private long idCondutor;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", referencedColumnName = "id")
    private Veiculo veiculo;

}

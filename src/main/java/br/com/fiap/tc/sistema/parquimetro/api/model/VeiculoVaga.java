package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.enums.StatusVagaEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "veiculoVaga")
public class VeiculoVaga {
    @Id
    private String id;

    @DBRef
    private Veiculo veiculo;

    @DBRef
    private Vaga vaga;

    @NonNull
    private LocalDateTime entrada;

    @NonNull
    private LocalDateTime saida;

    @DBRef
    private Periodo periodo;

    private StatusVagaEnum statusVagaEnum;
}

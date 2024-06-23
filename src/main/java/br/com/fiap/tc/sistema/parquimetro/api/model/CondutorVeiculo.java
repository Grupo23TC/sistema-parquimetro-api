package br.com.fiap.tc.sistema.parquimetro.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "condutorVeiculo")
public class CondutorVeiculo {
    @Id
    private String id;
    @DBRef
    private Condutor condutor;
    @DBRef
    private Veiculo veiculo;
}

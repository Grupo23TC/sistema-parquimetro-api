package br.com.fiap.tc.sistema.parquimetro.api.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vaga")
public class Vaga {
    @Id
    private Long id;
    private String endereco;
}

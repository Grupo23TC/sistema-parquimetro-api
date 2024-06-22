package br.com.fiap.tc.sistema.parquimetro.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endereco;
}

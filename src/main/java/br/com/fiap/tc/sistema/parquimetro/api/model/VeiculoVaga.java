package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.enums.StatusVagaEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class VeiculoVaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long idVeiculo;

    @NonNull
    private Long idVaga;

    @NonNull
    private LocalDateTime entrada;

    @NonNull
    private LocalDateTime saida;

    private Long idPeriodo;

    @Enumerated(EnumType.STRING)
    private StatusVagaEnum statusVagaEnum;
}

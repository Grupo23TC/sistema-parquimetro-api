package br.com.fiap.tc.sistema.parquimetro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Locacao {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Condutor condutor;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Vaga vaga;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Periodo periodo;
    @Schema(example = "2024-02-15 15:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime inicio;
    @Schema(example = "2024-02-15T18:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime fim;

}

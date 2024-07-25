package br.com.fiap.tc.sistema.parquimetro.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Veiculo {
    @NonNull
    @Schema(example = "AAA0A00", requiredMode = Schema.RequiredMode.REQUIRED)
    private String placa;
    @NonNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String tipo;
    private String marca;
    private String modelo;
    private Integer ano;
    private String cor;
}

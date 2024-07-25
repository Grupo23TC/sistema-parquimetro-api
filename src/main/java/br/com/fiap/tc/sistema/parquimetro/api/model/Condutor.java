package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.model.dto.EnderecoDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "condutor")
public class Condutor {
    @Id
    private String id;

    @NonNull
    @Schema(example = "Lucas Franco", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NonNull
    @Schema(example = "00912345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefone;

    @NonNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private EnderecoDTO endereco;

    @NonNull
    @Schema(example = "condutor@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NonNull
    @Schema(example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cpf;

    @NonNull
    @Schema(description = "Fomra de pagamento preferida escolhida pelo usuário.", example = "PIX", requiredMode = Schema.RequiredMode.REQUIRED)
    private FormaPagamentoEnum formaPagamento;

    @NonNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Veiculo> veiculos;
}
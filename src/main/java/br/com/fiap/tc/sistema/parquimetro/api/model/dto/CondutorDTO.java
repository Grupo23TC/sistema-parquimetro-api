package br.com.fiap.tc.sistema.parquimetro.api.model.dto;

import br.com.fiap.tc.sistema.parquimetro.api.model.Veiculo;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record CondutorDTO(

        String id,
        @NotBlank(message = "Nome é obrigatório")
        @Schema(example = "Lucas Franco", requiredMode = Schema.RequiredMode.REQUIRED)
        String nome,
        @NotBlank(message = "Telefone é obrigatório")
        @Schema(example = "00912345678", requiredMode = Schema.RequiredMode.REQUIRED)
        String telefone,
        @Email(message = "Email inválido")
        @Schema(example = "condutor@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
        String email,
        @CPF(message = "CPF inválido")
        @Schema(example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
        String cpf,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        EnderecoDTO endereco,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
        List<Veiculo> veiculos,
        @Schema(description = "Fomra de pagamento preferida escolhida pelo usuário.", example = "PIX", requiredMode = Schema.RequiredMode.REQUIRED)
        FormaPagamentoEnum formaPagamento

) {
}

package br.com.fiap.tc.sistema.parquimetro.api.model.dto;

import br.com.fiap.tc.sistema.parquimetro.api.model.Veiculo;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record CondutorDTO(

        String id,
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Telefone é obrigatório")
        String telefone,
        @Email(message = "Email inválido")
        String email,
        @CPF(message = "CPF inválido")
        String CPF,
        EnderecoDTO endereco,
        List<Veiculo> veiculos,
        FormaPagamentoEnum formaPagamento

) {
}

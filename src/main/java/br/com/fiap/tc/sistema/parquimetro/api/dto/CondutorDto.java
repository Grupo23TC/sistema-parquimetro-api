package br.com.fiap.tc.sistema.parquimetro.api.dto;

import br.com.fiap.tc.sistema.parquimetro.api.enums.FormaPagamentoEnum;
import br.com.fiap.tc.sistema.parquimetro.api.model.CondutorVeiculo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

public record CondutorDto(

        String id,
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "Telefone é obrigatório")
        String telefone,
        @Email(message = "Email inválido")
        String email,
        @CPF(message = "CPF inválido")
        String cpf,
        List<CondutorVeiculo> veiculos,
        FormaPagamentoEnum formaPagamento

) {
}

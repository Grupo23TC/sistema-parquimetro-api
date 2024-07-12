package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.model.dto.EnderecoDTO;
import br.com.fiap.tc.sistema.parquimetro.api.model.enums.FormaPagamentoEnum;
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
@Document(collection = "condutor")
public class Condutor {
    @Id
    private String id;

    private String nome;

    private String telefone;

    private EnderecoDTO endereco;

    private String email;

    private String CPF;

    private FormaPagamentoEnum formaPagamento;

    private List<Veiculo> veiculos;
}
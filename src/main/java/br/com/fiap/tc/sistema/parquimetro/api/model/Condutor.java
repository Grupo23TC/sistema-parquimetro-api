package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.enums.FormaPagamentoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@Document(collection = "condutor")
public class Condutor {

    @Id
    private String id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;

    @DBRef
    private List<Condutor_Veiculo> veiculosList;

    @NonNull
    private FormaPagamentoEnum formaPagamento;
}

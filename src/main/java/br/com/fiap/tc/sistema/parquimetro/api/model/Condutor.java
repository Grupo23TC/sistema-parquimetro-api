package br.com.fiap.tc.sistema.parquimetro.api.model;

import br.com.fiap.tc.sistema.parquimetro.api.enums.FormaPagamentoEnum;
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
@RequiredArgsConstructor
@Document(collection = "condutor")
public class Condutor {
    @Id
    private String id;
    @NonNull
    private String nome;
    @NonNull
    private String telefone;
    @NonNull
    private String email;
    @NonNull
    private String cpf;

    @DBRef
    private List<CondutorVeiculo> veiculos = new ArrayList<>();

    @NonNull
    private FormaPagamentoEnum formaPagamento;
}

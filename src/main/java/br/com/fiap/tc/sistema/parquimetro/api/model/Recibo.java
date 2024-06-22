package br.com.fiap.tc.sistema.parquimetro.api.model;

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
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", referencedColumnName = "id")
    private Veiculo veiculo;

    private LocalDateTime entrada;

    private LocalDateTime saida;

    @OneToOne
    @JoinColumn(name = "id_pagamento", referencedColumnName = "id")
    private Pagamento pagamento;
}

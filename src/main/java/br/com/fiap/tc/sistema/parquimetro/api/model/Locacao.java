package br.com.fiap.tc.sistema.parquimetro.api.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Locacao {

    private Condutor condutor;
    private Vaga vaga;
    private Periodo periodo;
    private LocalDateTime inicio;
    private LocalDateTime fim;

}

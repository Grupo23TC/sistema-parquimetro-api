package br.com.fiap.tc.sistema.parquimetro.api.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StatusVagaEnum {
    OCUPADA,
    DISPONIVEL
}

package br.com.fiap.tc.sistema.parquimetro.api.model.dto;

public record EnderecoDTO (

        String rua,
        String complemento,
        String cidade,
        String estado,
        String cep
){
}

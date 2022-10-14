package com.gabriel.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoRequest {

    @NotBlank
    private String cep;
    @NotBlank
    private String numero;
    @NotBlank
    private String logradouro;
    private String complemento;
    @NotBlank
    private String bairro;
    @Valid
    @NotNull
    private CidadeIdRequest cidade;
}

package com.gabriel.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

    private String cep;
    private String numero;
    private String logradouro;
    private String complemento;
    private String bairro;
    private CidadeResumoModel cidade;
//    private String cidade;
//    private String estado;
}

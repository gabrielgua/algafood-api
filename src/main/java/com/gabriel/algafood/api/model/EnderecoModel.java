package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {

    @ApiModelProperty(example = "38400-000")
    private String cep;

    @ApiModelProperty(example = "102")
    private String numero;

    @ApiModelProperty(example = "Rua Floriano Peixoto")
    private String logradouro;

    @ApiModelProperty(example = "Apto 801")
    private String complemento;

    @ApiModelProperty(example = "Água Verde")
    private String bairro;

    private CidadeResumoModel cidade;
//    private String cidade;
//    private String estado;
}

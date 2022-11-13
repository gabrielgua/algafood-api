package com.gabriel.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EnderecoRequest {

    @ApiModelProperty(example = "29900-502", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "144", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Rua Governador Afonso Cláudio", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "Apto 233")
    private String complemento;

    @ApiModelProperty(example = "Centro", required = true)
    @NotBlank
    private String bairro;


    @Valid
    @NotNull
    private CidadeIdRequest cidade;
}

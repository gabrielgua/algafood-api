package com.gabriel.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeRequest {

    @ApiModelProperty(example = "Curitiba", required = true)
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdRequest estado;
}

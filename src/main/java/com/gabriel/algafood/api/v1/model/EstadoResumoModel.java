package com.gabriel.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoResumoModel {

    @ApiModelProperty(example = "Paraná")
    private String nome;
}

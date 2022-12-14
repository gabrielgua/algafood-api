package com.gabriel.algafood.api.v1.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GrupoRequest {

    @ApiModelProperty(example = "Gerente", required = true)
    @NotBlank
    private String nome;
}

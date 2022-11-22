package com.gabriel.algafood.api.v1.model.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaRequest {

    @ApiModelProperty(example = "Alemã", required = true)
    @NotBlank
    private String nome;
}

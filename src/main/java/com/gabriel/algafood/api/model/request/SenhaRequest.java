package com.gabriel.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaRequest {

    @ApiModelProperty(example = "123123", required = true)
    @NotBlank
    private String senhaAtual;

    @ApiModelProperty(example = "321321", required = true)
    @NotBlank
    private String senhaNova;
}

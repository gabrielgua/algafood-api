package com.gabriel.algafood.api.v1.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioComSenhaRequest extends UsuarioRequest {

    @ApiModelProperty(example = "123123")
    @NotBlank
    private String senha;
}

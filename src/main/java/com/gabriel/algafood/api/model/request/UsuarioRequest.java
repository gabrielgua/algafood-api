package com.gabriel.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioRequest {

    @ApiModelProperty(example = "Gabriel da Silva")
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "gabriel.silva@algafood.com.br")
    @Email
    @NotBlank
    private String email;
}

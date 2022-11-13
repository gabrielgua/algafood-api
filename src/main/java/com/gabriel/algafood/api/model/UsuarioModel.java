package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UsuarioModel {

    @ApiModelProperty(example = "6")
    private Long id;

    @ApiModelProperty(example = "Débora Mendonça")
    private String nome;

    @ApiModelProperty(example = "debora.mendonca@algafood.com.br")
    private String email;
}

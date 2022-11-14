package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoModel {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Admin")
    private String nome;

    @ApiModelProperty(example = "Adminitrador do sistema")
    private String descricao;
}

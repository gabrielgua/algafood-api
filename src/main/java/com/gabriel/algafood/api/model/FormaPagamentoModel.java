package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Pix")
    private String descricao;
}

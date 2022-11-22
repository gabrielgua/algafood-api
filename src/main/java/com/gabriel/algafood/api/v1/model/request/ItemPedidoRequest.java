package com.gabriel.algafood.api.v1.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemPedidoRequest {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long produtoId;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Integer quantidade;

    @ApiModelProperty(example = "Sem cebola")
    private String observacao;

}

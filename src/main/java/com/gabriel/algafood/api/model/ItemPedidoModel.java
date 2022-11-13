package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoModel {

    @ApiModelProperty(example = "1")
    private Long produtoId;

    @ApiModelProperty(example = "Porco com molho agridoce")
    private String produtoNome;

    @ApiModelProperty(example = "1")
    private Integer quantidade;

    @ApiModelProperty(example = "78.90")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "78.90")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Menos picante por favor")
    private String observacao;
}

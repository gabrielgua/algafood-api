package com.gabriel.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemPedidoRequest {

    @NotNull
    private Long produtoId;
    @NotNull
    private Integer quantidade;
    private String observacao;

}

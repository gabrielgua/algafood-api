package com.gabriel.algafood.domain.event;

import com.gabriel.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {
    private Pedido pedido;

}

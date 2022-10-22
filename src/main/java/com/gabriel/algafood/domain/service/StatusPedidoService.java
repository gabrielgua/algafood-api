package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Pedido;
import com.gabriel.algafood.domain.model.StatusPedido;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class StatusPedidoService {

    //criado -> cancelado
    //confirmado -> entregue

    private PedidoService pedidoService;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = pedidoService.buscarPorId(codigoPedido);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(String codigoPedido) {
        Pedido pedido = pedidoService.buscarPorId(codigoPedido);
        pedido.cancelar();
    }

    @Transactional
    public void confirmarEntrega(String codigoPedido) {
        Pedido pedido = pedidoService.buscarPorId(codigoPedido);
        pedido.entregar();
    }
}

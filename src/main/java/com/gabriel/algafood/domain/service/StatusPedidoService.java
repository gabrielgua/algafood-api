package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.model.Pedido;
import com.gabriel.algafood.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StatusPedidoService {

    private PedidoService pedidoService;
    private PedidoRepository repository;

    @Transactional
    public void confirmar(String codigoPedido) {
        Pedido pedido = pedidoService.buscarPorId(codigoPedido);
        pedido.confirmar();

        repository.save(pedido);
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

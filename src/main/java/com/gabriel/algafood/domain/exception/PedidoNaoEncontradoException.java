package com.gabriel.algafood.domain.exception;


public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format("Pedido não encontrado, id: '%s'", codigoPedido));
    }
}



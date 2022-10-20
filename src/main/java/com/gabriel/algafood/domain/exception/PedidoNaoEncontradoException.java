package com.gabriel.algafood.domain.exception;


public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(Long id) {
        this("Pedido não encontrado, id: #" + id);
    }
}



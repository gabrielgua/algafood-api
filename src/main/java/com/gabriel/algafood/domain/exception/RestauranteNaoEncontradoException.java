package com.gabriel.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this("Restaurante não encontrado, id: #"+id);
    }
}

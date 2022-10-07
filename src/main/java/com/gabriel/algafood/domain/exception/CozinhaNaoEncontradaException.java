package com.gabriel.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this("Cozinha não encontrada, id: #"+id);
    }
}

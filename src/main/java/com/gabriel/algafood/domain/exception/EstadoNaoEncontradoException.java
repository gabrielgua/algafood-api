package com.gabriel.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

    public EstadoNaoEncontradoException(Long id) {
        this("Estado não encontrado, id: #"+id);
    }
}

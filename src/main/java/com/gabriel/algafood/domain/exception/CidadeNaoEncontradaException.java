package com.gabriel.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long id) {
        this("Cidade não encontrada, id: #"+id);
    }
}

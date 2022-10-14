package com.gabriel.algafood.domain.exception;


public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long id) {
        this("Grupo não encontrado, id: #" + id);
    }
}

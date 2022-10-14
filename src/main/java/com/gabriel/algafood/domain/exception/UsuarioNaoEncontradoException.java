package com.gabriel.algafood.domain.exception;


public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this("Usuário não encontrado, id: #"+id);
    }
}

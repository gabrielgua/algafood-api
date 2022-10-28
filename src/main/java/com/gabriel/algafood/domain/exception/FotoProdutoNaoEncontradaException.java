package com.gabriel.algafood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException{

    public FotoProdutoNaoEncontradaException(String message) {
        super(message);
    }

    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format("Foto do Produto de id: '#%d' não encontrada para o restaurante de id: '#%d'", produtoId, restauranteId));
    }
}

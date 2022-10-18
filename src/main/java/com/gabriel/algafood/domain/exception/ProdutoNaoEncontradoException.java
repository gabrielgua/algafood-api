package com.gabriel.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this(String.format("Produto id: #%s, não encontrado para o Restaurante id: #%s", produtoId, restauranteId));
    }

}

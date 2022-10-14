package com.gabriel.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException{
    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException (Long id) {
        this("Forma de pagamento não encontrado, id: #"+id);
    }
}

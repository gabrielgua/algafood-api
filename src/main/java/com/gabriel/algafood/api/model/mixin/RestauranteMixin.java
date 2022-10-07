package com.gabriel.algafood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Endereco;
import com.gabriel.algafood.domain.model.FormaPagamento;
import com.gabriel.algafood.domain.model.Produto;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

public abstract class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

//    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private Set<Produto> produtos;

    @JsonIgnore
    private Set<FormaPagamento> formasPagamento = new HashSet<>();
}

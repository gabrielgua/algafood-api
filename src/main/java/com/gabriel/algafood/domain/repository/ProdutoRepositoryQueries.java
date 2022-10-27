package com.gabriel.algafood.domain.repository;

import com.gabriel.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto save(FotoProduto fotoProduto);
    void delete(FotoProduto foto);
}

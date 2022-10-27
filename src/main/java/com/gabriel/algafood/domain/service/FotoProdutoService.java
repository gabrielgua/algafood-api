package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.model.FotoProduto;
import com.gabriel.algafood.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FotoProdutoService {

    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();
        var fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
        }

        return produtoRepository.save(foto);
    }
}

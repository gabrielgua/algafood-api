package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.gabriel.algafood.domain.model.Produto;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private ProdutoRepository repository;

    public List<Produto> listar(Restaurante restaurante) {
        return repository.findByRestaurante(restaurante);
    }

    public Produto buscarPorId(Long restauranteId, Long produtoId) {
        return repository.findById(restauranteId, produtoId).orElseThrow((() -> new ProdutoNaoEncontradoException(restauranteId, produtoId)));
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }
}

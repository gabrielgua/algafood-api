package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.gabriel.algafood.domain.model.Produto;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProdutoService {

    private ProdutoRepository repository;

    public List<Produto> listar(Restaurante restaurante) {
        return repository.findByRestaurante(restaurante);
    }

    public List<Produto> listar(Restaurante restaurante, Boolean ativo) {
        return repository.findByRestauranteAndAtivo(restaurante, ativo);
    }

    public Produto buscarPorId(Long restauranteId, Long produtoId) {
        return repository.findById(restauranteId, produtoId).orElseThrow((() -> new ProdutoNaoEncontradoException(restauranteId, produtoId)));
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    @Transactional
    public void ativarOuInativar(Long restauranteId, Long produtoId) {
        var produto = buscarPorId(restauranteId, produtoId);
        produto.ativoOuInativo();
    }
}

package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.ProdutoAssembler;
import com.gabriel.algafood.api.model.ProdutoModel;
import com.gabriel.algafood.api.model.request.ProdutoRequest;
import com.gabriel.algafood.domain.model.Produto;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.service.ProdutoService;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
    private RestauranteService restauranteService;
    private ProdutoService produtoService;
    private ProdutoAssembler assembler;

    @GetMapping
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        return assembler.toCollectionModel(produtoService.listar(restaurante));
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscarPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = produtoService.buscarPorId(restaurante.getId(), produtoId);

        return assembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoRequest request) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = assembler.toEntity(request);
        produto.setRestaurante(restaurante);
        return assembler.toModel(produtoService.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    public ProdutoModel editar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoRequest request) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = produtoService.buscarPorId(restaurante.getId(), produtoId);
        assembler.copyToEntity(request, produto);
        return assembler.toModel(produtoService.salvar(produto));
    }

}














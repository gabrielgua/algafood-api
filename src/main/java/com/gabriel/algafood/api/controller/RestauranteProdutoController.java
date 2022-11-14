package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.ProdutoAssembler;
import com.gabriel.algafood.api.model.ProdutoModel;
import com.gabriel.algafood.api.model.request.ProdutoRequest;
import com.gabriel.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.gabriel.algafood.domain.model.Produto;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.service.ProdutoService;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {
    private RestauranteService restauranteService;
    private ProdutoService produtoService;
    private ProdutoAssembler assembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProdutoModel> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        return assembler.toCollectionModel(produtoService.listar(restaurante));
    }

    @GetMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel buscarPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = produtoService.buscarPorId(restaurante.getId(), produtoId);

        return assembler.toModel(produto);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoRequest request) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = assembler.toEntity(request);
        produto.setRestaurante(restaurante);
        return assembler.toModel(produtoService.salvar(produto));
    }

    @PutMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel editar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoRequest request) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = produtoService.buscarPorId(restaurante.getId(), produtoId);
        assembler.copyToEntity(request, produto);
        return assembler.toModel(produtoService.salvar(produto));
    }

    @PutMapping("/{produtoId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarOuInativar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        produtoService.ativarOuInativar(restaurante.getId(), produtoId);
    }

    @GetMapping(params = "view=ativos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProdutoModel> listarAtivos(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        List<Produto> produtosAtivos = produtoService.listar(restaurante, true);
        return assembler.toCollectionModel(produtosAtivos);
    }

    @GetMapping(params = "view=inativos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProdutoModel> listarInativos(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        List<Produto> produtosInativos = produtoService.listar(restaurante, false);
        return assembler.toCollectionModel(produtosInativos);
    }


}














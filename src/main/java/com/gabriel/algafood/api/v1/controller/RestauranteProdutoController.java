package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.assembler.ProdutoAssembler;
import com.gabriel.algafood.api.v1.model.ProdutoModel;
import com.gabriel.algafood.api.v1.model.request.ProdutoRequest;
import com.gabriel.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.model.Produto;
import com.gabriel.algafood.domain.service.ProdutoService;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {
    private RestauranteService restauranteService;
    private ProdutoService produtoService;
    private ProdutoAssembler assembler;
    private ApiLinks links;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        return assembler.toCollectionModel(produtoService.listar(restaurante))
                .add(links.linkToProdutos(restauranteId));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel buscarPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = produtoService.buscarPorId(restaurante.getId(), produtoId);

        return assembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel salvar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoRequest request) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = assembler.toEntity(request);
        produto.setRestaurante(restaurante);
        return assembler.toModel(produtoService.salvar(produto));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoModel editar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoRequest request) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        Produto produto = produtoService.buscarPorId(restaurante.getId(), produtoId);
        assembler.copyToEntity(request, produto);
        return assembler.toModel(produtoService.salvar(produto));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{produtoId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarOuInativar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        produtoService.ativarOuInativar(restaurante.getId(), produtoId);
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(params = "view=ativos", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<ProdutoModel> listarAtivos(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        List<Produto> produtosAtivos = produtoService.listar(restaurante, true);
        return assembler.toCollectionModel(produtosAtivos);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @GetMapping(params = "view=inativos", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<ProdutoModel> listarInativos(@PathVariable Long restauranteId) {
        var restaurante = restauranteService.buscarPorId(restauranteId);
        List<Produto> produtosInativos = produtoService.listar(restaurante, false);
        return assembler.toCollectionModel(produtosInativos);
    }


}














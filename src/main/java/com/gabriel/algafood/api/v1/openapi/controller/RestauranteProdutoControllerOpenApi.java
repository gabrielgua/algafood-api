package com.gabriel.algafood.api.v1.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.v1.model.ProdutoModel;
import com.gabriel.algafood.api.v1.model.request.ProdutoRequest;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

    @ApiOperation("Lista os produtos de um restaurante por um ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "ativos ou inativos", allowableValues = "ativos,inativos",
                    name = "view", paramType = "query", type = "string")

    })
    CollectionModel<ProdutoModel> listar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

    @ApiOperation("Busca um produto de um restaurante pelos IDs")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou do produto iválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
    })
    ProdutoModel buscarPorId(
            @ApiParam(value = "ID de um restaurante", required = true) Long restauranteId,
            @ApiParam(value = "ID de um produto", required = true) Long produtoId);

    @ApiOperation("Cadastra um novo produto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ProdutoModel salvar(
            @ApiParam(value = "ID de um restaurante", required = true) Long restauranteId,
            @ApiParam(name = "Corpo", value = "Representação de um produto", required = true) ProdutoRequest request);

    @ApiOperation("Atualiza um produto por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado"),
            @ApiResponse(code = 404, message = "Produto ou restaurante não encontrado", response = Problem.class)
    })
    ProdutoModel editar(
            @ApiParam(value = "ID de um restaurante", required = true) Long restauranteId,
            @ApiParam(value = "ID de um produto", required = true) Long produtoId,
            @ApiParam(name = "Corpo", value = "Representação de um produto com novos dados", required = true) ProdutoRequest request);

    @ApiOperation("Ativa ou inativa um produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Aplicado o oposto do status de ativação atual", response = Problem.class),
            @ApiResponse(code = 404, message = "Produto ou restaurante não encontrado", response = Problem.class)
    })
    void ativarOuInativar(Long restauranteId, Long produtoId);

    @ApiOperation(value = "Lista apenas os produtos ativos", hidden = true)
    @ApiResponse(code = 404, message = "Restaurante não econtrado", response = Problem.class)
   CollectionModel<ProdutoModel> listarAtivos(Long restauranteId);


    @ApiOperation(value = "Lista apenas os produtos invativos", hidden = true)
    @ApiResponse(code = 404, message = "Restaurante não econtrado", response = Problem.class)
    CollectionModel<ProdutoModel> listarInativos(Long restauranteId);
}

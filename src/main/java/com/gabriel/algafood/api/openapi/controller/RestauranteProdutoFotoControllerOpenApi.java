package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.FotoProdutoModel;
import com.gabriel.algafood.api.model.request.FotoProdutoRequest;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenApi {

    @ApiOperation(value = "Busca uma foto de um produto", produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante e/ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante ou produto ou foto do produto não encontrado", response = Problem.class)
    })
    public FotoProdutoModel buscarPorId(
            @ApiParam(value = "ID de um restaurante", required = true) Long restauranteId,
            @ApiParam(value = "ID de um produto", required = true) Long produtoId);

    @ApiOperation(value = "Busca uma foto de um produto", hidden = true)
    public ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Atualiza uma foto de um produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto atualizada"),
            @ApiResponse(code = 404, message = "Restaurante ou produto não encontrado", response = Problem.class)
    })
    public FotoProdutoModel atualizarFoto(
            @ApiParam(value = "ID de um restaurante", required = true) Long restauranteId,
            @ApiParam(value = "ID de um produto", required = true) Long produtoId,
            @ApiParam(name = "Corpo", value = "Representação de uma foto", required = true) FotoProdutoRequest fotoProdutoRequest) throws IOException;

    @ApiOperation("Remove uma foto de um produto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto removida"),
            @ApiResponse(code = 404, message = "Restaurante ou produto ou foto do produto não encontrado", response = Problem.class)
    })
    public void removerFoto(
            @ApiParam(value = "ID de um restaurante", required = true) Long restauranteId,
            @ApiParam(value = "ID de um produto", required = true) Long produtoId);

}

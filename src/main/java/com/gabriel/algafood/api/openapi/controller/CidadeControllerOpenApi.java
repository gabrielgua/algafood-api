package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.CidadeModel;
import com.gabriel.algafood.api.model.request.CidadeRequest;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeModel> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeModel buscarPorId(
            @ApiParam(value = "ID de uma cidade", example = "1")
            Long id);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponse(code = 201, message = "Cidade cadastrada")
    CidadeModel salvar(
            @ApiParam(name = "Corpo", value = "Representação de uma nova cidade")
            CidadeRequest request);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeModel editar(
            @ApiParam(name = "Corpo", value = "Representação de uma cidade com os novos dados")
            CidadeRequest request,
            @ApiParam(value = "ID de uma cidade", example = "2")
            Long id);

    @ApiOperation("Remove uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade removida"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(
            @ApiParam("ID de uma cidade")
            Long id);

}

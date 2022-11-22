package com.gabriel.algafood.api.v2.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.v2.model.CidadeModelV2;
import com.gabriel.algafood.api.v2.model.request.CidadeRequestV2;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApiV2 {

    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeModelV2> listar();

    @ApiOperation("Busca um cidade por id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeModelV2 buscarPorId(@ApiParam(value = "Id de uma cidade", required = true) Long id);


    @ApiOperation("Adiciona um nova cidade")
    @ApiResponse(code = 201, message = "Cidade cadastrada com sucesso")
    CidadeModelV2 salvar(
            @ApiParam(name = "Corpo", value = "Representação de uma cidade", required = true) CidadeRequestV2 requestV2);

    @ApiOperation("Atualiza uma cidade por id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    CidadeModelV2 editar(
            @ApiParam(value = "Id de uma cidade", required = true) Long id,
            @ApiParam(name = "Corpo", value = "Representação de uma cidade com novos dados", required = true) CidadeRequestV2 requestV2);

    @ApiOperation("Remove uma cidade por id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade removida com sucesso"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    void remover(
            @ApiParam(value = "Id de uma cidade") Long id);
}

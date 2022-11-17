package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.EstadoModel;
import com.gabriel.algafood.api.model.request.EstadoRequest;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

    @ApiOperation("Lista os estados")
    CollectionModel<EstadoModel> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoModel buscarPorId(@ApiParam(value = "ID de um estado", required = true) Long id);

    @ApiOperation("Cadastra um novo estado")
    @ApiResponse(code = 201, message = "Estado cadastrado")
    EstadoModel salvar(@ApiParam(name = "Corpo", value = "Representação de um estado", required = true) EstadoRequest request);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoModel editar(
            @ApiParam(name = "Corpo", value = "Representação de um estado com novos dados", required = true) EstadoRequest request,
            @ApiParam(value = "ID de um estado", required = true) Long id);

    @ApiOperation("Remove um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado removido"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um estado", required = true) Long id);
}

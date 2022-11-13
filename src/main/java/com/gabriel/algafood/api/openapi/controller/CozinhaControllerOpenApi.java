package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.CozinhaModel;
import com.gabriel.algafood.api.model.request.CozinhaRequest;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista as cozinhas")
    public Page<CozinhaModel> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModel buscarPorId(@ApiParam(value = "ID de uma cozinha", example = "1") Long id);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponse(code = 201, message = "Cozinha cadastrada")
    public CozinhaModel adicionar(@ApiParam(name = "Corpo", value = "Representação de uma nova cozinha") CozinhaRequest request);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModel editar(
            @ApiParam(name = "Corpo", value = "Representação de uma cozinha com novos dados") CozinhaRequest request,
            @ApiParam(value = "ID de uma cozinha") Long id);

    @ApiOperation("Remove uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha removida"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID de uma cozinha") Long id);
}

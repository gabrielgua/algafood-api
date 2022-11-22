package com.gabriel.algafood.api.v2.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.v2.model.CozinhaModelV2;
import com.gabriel.algafood.api.v2.model.request.CozinhaRequestV2;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApiV2 {

    @ApiOperation("Lista as cozinhas")
    PagedModel<CozinhaModelV2> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaModelV2 buscarPorId(@ApiParam(value = "Id de uma cozinha", example = "1") Long id);

    @ApiOperation("Adiciona uma nova cozinha")
    @ApiResponse(code = 201, message = "Cozinha cadastrada com sucesso")
    CozinhaModelV2 salvar(
            @ApiParam(name = "Corpo", value = "Representação de uma nova cozinha") CozinhaRequestV2 request);

    @ApiOperation("Atualiza uma cozinha por id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada com sucesso"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    CozinhaModelV2 editar(
            @ApiParam(value = "Id de uma cozinha") Long id,
            @ApiParam(name = "Corpo", value = "Representação de uma cozinha com novos dados") CozinhaRequestV2 request);

    @ApiOperation("Remove uma cozinha por id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha removida com sucesso"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void remover(@ApiParam(value = "Id de uma cozinha") Long id);
}

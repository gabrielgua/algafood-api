package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.UsuarioModel;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários responsáveis por um determinado restaurante por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    List<UsuarioModel> listarResponsaveis(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

    @ApiOperation("Vincula um usuário com um restaurante pelos IDs")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário vinculado ao restaurante"),
            @ApiResponse(code = 404, message = "Usuário ou restaurante não encontrados", response = Problem.class)
    })
    void vincularResponsavel(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId,
                                    @ApiParam(value = "ID do usuário", required = true) Long usuarioId);

    @ApiOperation("Desvincula um usuário com um restaurante pelos IDs")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Usuário desvinculado do restaurante"),
            @ApiResponse(code = 404, message = "Usuário ou restaurante não encontrados", response = Problem.class)
    })
    void desvincularResponsavel(@ApiParam(value = "ID do restaurante", required = true) Long restauranteId,
                                       @ApiParam(value = "ID do usuário") Long usuarioId);
}

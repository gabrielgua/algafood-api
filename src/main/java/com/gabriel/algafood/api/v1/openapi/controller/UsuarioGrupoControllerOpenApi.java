package com.gabriel.algafood.api.v1.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.v1.model.GrupoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

    @ApiOperation("Lista os grupos de um usuário")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    CollectionModel<GrupoModel> listarGrupos(@ApiParam(value = "ID de um usuário", required = true) Long usuarioId);

    @ApiOperation("Vincula um grupo a um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo vinculado"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> vincularGrupo(
            @ApiParam(value = "ID de um usuário", required = true) Long usuarioId,
            @ApiParam(value = "ID de um grupo", required = true) Long grupoId);

    @ApiOperation("Desvincula um grupo a um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo desvinculado"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado", response = Problem.class)
    })
    ResponseEntity<Void> desvincularGrupo(
            @ApiParam(value = "ID de um usuário", required = true) Long usuarioId,
            @ApiParam(value = "ID de um grupo", required = true) Long grupoId);
}

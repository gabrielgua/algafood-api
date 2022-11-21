package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.PermissaoModel;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões de um determinado grupo")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
     CollectionModel<PermissaoModel> listarPermissoes(@ApiParam(value = "ID de um grupo", required = true) Long grupoId);

    @ApiOperation("Vincula uma permissão com um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Permissão vinculada"),
            @ApiResponse(code = 404, message = "Permissão ou grupo não encontrado", response = Problem.class)
    })
     ResponseEntity<Void> vincularPermissao(
            @ApiParam(value = "ID de um grupo", required = true) Long grupoId,
            @ApiParam(value = "ID de uma permissão", required = true) Long permissaoId);

    @ApiOperation("Desvincula uma permissão com um grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Permissão desvinculada"),
            @ApiResponse(code = 404, message = "Permissão ou grupo não encontrado", response = Problem.class)
    })
     ResponseEntity<Void> desvincularPermissao(
            @ApiParam(value = "ID de um grupo", required = true) Long grupoId,
            @ApiParam(value = "ID de uma permissão", required = true) Long permissaoId);
}
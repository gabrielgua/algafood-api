package com.gabriel.algafood.api.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.model.GrupoModel;
import com.gabriel.algafood.api.model.request.GrupoRequest;
import io.swagger.annotations.*;

import java.util.List;
@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

    @ApiOperation("Lista os grupos")
    public List<GrupoModel> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoModel buscarPorId(@ApiParam(value = "ID do grupo") Long id);

    @ApiOperation("Cadastra um grupo")
    @ApiResponse(code = 201, message = "Grupo cadastrado")
    public GrupoModel salvar(@ApiParam(name = "Corpo", value = "Representação de novo grupo") GrupoRequest request);


    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public GrupoModel editar(@ApiParam(value = "ID do grupo") Long id,@ApiParam(value = "Representação de um grupo com os novos dados") GrupoRequest request);

    @ApiOperation("Remove um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo removido"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    public void remover(@ApiParam(value = "ID do grupo") Long id);
}

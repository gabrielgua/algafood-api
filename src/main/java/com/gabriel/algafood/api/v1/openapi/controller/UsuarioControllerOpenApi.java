package com.gabriel.algafood.api.v1.openapi.controller;

import com.gabriel.algafood.api.exceptionhandler.Problem;
import com.gabriel.algafood.api.v1.model.UsuarioModel;
import com.gabriel.algafood.api.v1.model.request.SenhaRequest;
import com.gabriel.algafood.api.v1.model.request.UsuarioComSenhaRequest;
import com.gabriel.algafood.api.v1.model.request.UsuarioRequest;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

    @ApiOperation("Lista os usuários")
    CollectionModel<UsuarioModel> listar();

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioModel buscarPorId(@ApiParam(value = "ID de um usuário", required = true) Long id);

    @ApiOperation("Cadastra um novo usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado")
    })
    UsuarioModel salvar(@ApiParam(name = "Corpo", value = "Representação de um usuário com senha", required = true) UsuarioComSenhaRequest request);

    @ApiOperation("Atualiza um usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioModel editar(
            @ApiParam(value = "ID de um usuário", required = true) Long id,
            @ApiParam(name = "Corpo", value = "Representação de um usuário sem a senha", required = true) UsuarioRequest request);

    @ApiOperation("Remove um usuário")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 204, message = "Usuário removido"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um usuário", required = true) Long id);

    @ApiOperation("Altera senha de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha alterada"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void alterarSenha(
            @ApiParam(value = "ID de um usuário", required = true) Long id,
            @ApiParam(name = "Corpo", value = "Representação da entidade para troca de senha", required = false) SenhaRequest request);
}

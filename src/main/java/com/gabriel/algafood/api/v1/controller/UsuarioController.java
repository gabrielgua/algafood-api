package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.UsuarioAssembler;
import com.gabriel.algafood.api.v1.model.UsuarioModel;
import com.gabriel.algafood.api.v1.model.request.SenhaRequest;
import com.gabriel.algafood.api.v1.model.request.UsuarioComSenhaRequest;
import com.gabriel.algafood.api.v1.model.request.UsuarioRequest;
import com.gabriel.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    private UsuarioService service;
    private UsuarioAssembler assembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UsuarioModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultarUsuario
    @GetMapping(path = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel buscarPorId(@PathVariable Long usuarioId) {
        return assembler.toModel(service.buscarPorId(usuarioId));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeCadastrar
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel salvar(@RequestBody @Valid UsuarioComSenhaRequest request) {
        Usuario usuario = assembler.toEntity(request);
        return assembler.toModel(service.salvar(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping(path = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioModel editar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioRequest request) {
        Usuario usuario = service.buscarPorId(usuarioId);
        assembler.copyToEntity(request, usuario);
        return assembler.toModel(service.salvar(usuario));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @DeleteMapping("/{usuarioId}")
    public void remover(@PathVariable Long usuarioId) {
        service.remover(usuarioId);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{usuarioId}/senha")
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaRequest request) {
        service.alterarSenha(usuarioId, request.getSenhaAtual(), request.getSenhaNova());
    }





























}

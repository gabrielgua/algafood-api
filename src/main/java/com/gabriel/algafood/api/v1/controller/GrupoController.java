package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.GrupoAssembler;
import com.gabriel.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.gabriel.algafood.api.v1.model.GrupoModel;
import com.gabriel.algafood.api.v1.model.request.GrupoRequest;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.model.Grupo;
import com.gabriel.algafood.domain.service.GrupoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    private GrupoService service;
    private GrupoAssembler assembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GrupoModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel salvar(@RequestBody @Valid GrupoRequest request) {
        Grupo grupo = assembler.toEntity(request);
        return assembler.toModel(service.salvar(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel editar(@PathVariable Long id, @RequestBody @Valid GrupoRequest request) {
        Grupo grupo = service.buscarPorId(id);
        assembler.copyToEntity(request, grupo);
        return assembler.toModel(service.salvar(grupo));
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

}

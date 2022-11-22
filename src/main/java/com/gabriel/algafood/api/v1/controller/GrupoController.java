package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.GrupoAssembler;
import com.gabriel.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.gabriel.algafood.api.v1.model.GrupoModel;
import com.gabriel.algafood.api.v1.model.request.GrupoRequest;
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
@RequestMapping("grupos")
public class GrupoController implements GrupoControllerOpenApi {

    private GrupoService service;
    private GrupoAssembler assembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GrupoModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel salvar(@RequestBody @Valid GrupoRequest request) {
        Grupo grupo = assembler.toEntity(request);
        return assembler.toModel(service.salvar(grupo));
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GrupoModel editar(@PathVariable Long id, @RequestBody @Valid GrupoRequest request) {
        Grupo grupo = service.buscarPorId(id);
        assembler.copyToEntity(request, grupo);
        return assembler.toModel(service.salvar(grupo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }

}

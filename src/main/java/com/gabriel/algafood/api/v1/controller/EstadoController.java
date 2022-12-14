package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.EstadoAssembler;
import com.gabriel.algafood.api.v1.model.EstadoModel;
import com.gabriel.algafood.api.v1.model.request.EstadoRequest;
import com.gabriel.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.model.Estado;
import com.gabriel.algafood.domain.service.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/estados")
@AllArgsConstructor
public class EstadoController implements EstadoControllerOpenApi {

    private EstadoService service;
    private EstadoAssembler assembler;

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EstadoModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @CheckSecurity.Estados.PodeConsultar
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @CheckSecurity.Estados.PodeEditar
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel salvar(@RequestBody @Valid EstadoRequest request) {
        Estado estado = assembler.toEntity(request);
        return assembler.toModel(service.salvar(estado));
    }

    @CheckSecurity.Estados.PodeEditar
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoModel editar(@RequestBody @Valid EstadoRequest request, @PathVariable Long id) {
        Estado estado = service.buscarPorId(id);
        assembler.copyToEntity(request, estado);
        return assembler.toModel(service.salvar(estado));
    }

    @CheckSecurity.Estados.PodeEditar
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        Estado estado = service.buscarPorId(id);
        service.remover(estado.getId());
    }
















}

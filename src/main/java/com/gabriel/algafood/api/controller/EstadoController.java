package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.EstadoAssembler;
import com.gabriel.algafood.api.model.EstadoModel;
import com.gabriel.algafood.api.model.request.EstadoRequest;
import com.gabriel.algafood.api.openapi.controller.EstadoControllerOpenApi;
import com.gabriel.algafood.domain.model.Estado;
import com.gabriel.algafood.domain.service.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("estados")
@AllArgsConstructor
public class EstadoController implements EstadoControllerOpenApi {

    private EstadoService service;
    private EstadoAssembler assembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EstadoModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel salvar(@RequestBody @Valid EstadoRequest request) {
        Estado estado = assembler.toEntity(request);
        return assembler.toModel(service.salvar(estado));
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EstadoModel editar(@RequestBody @Valid EstadoRequest request, @PathVariable Long id) {
        Estado estado = service.buscarPorId(id);
        assembler.copyToEntity(request, estado);
        return assembler.toModel(service.salvar(estado));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        Estado estado = service.buscarPorId(id);
        service.remover(estado.getId());
    }
















}

package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.EstadoAssembler;
import com.gabriel.algafood.api.model.EstadoModel;
import com.gabriel.algafood.api.model.request.EstadoRequest;
import com.gabriel.algafood.domain.model.Estado;
import com.gabriel.algafood.domain.service.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("estados")
@AllArgsConstructor
public class EstadoController {

    private EstadoService service;
    private EstadoAssembler assembler;

    @GetMapping
    public List<EstadoModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @GetMapping("/{id}")
    public EstadoModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }

    @PostMapping
    public EstadoModel salvar(@RequestBody @Valid EstadoRequest request) {
        Estado estado = assembler.toEntity(request);
        return assembler.toModel(service.salvar(estado));
    }

    @PutMapping("/{id}")
    public EstadoModel editar(@RequestBody @Valid EstadoRequest request, @PathVariable Long id) {
        Estado estado = service.buscarPorId(id);
        assembler.copyToEntity(request, estado);
        return assembler.toModel(service.salvar(estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Estado estado = service.buscarPorId(id);
        service.remover(estado.getId());
        return ResponseEntity.noContent().build();
    }
















}

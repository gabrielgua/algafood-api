package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.domain.model.Estado;
import com.gabriel.algafood.domain.repository.EstadoRepository;
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

    @GetMapping
    public List<Estado> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public Estado salvar(@RequestBody @Valid Estado estado) {
        return service.salvar(estado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estado> editar(@RequestBody @Valid Estado estado, @PathVariable Long id) {
        service.buscarPorId(id);
        estado.setId(id);
        return ResponseEntity.ok(service.salvar(estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Estado estado = service.buscarPorId(id);
        service.remover(estado.getId());
        return ResponseEntity.noContent().build();
    }
















}

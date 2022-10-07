package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.service.CozinhaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("cozinhas")
public class CozinhaController {

    private CozinhaService service;

    @GetMapping
    public List<Cozinha> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Cozinha buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
        return service.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cozinha> editar(@RequestBody @Valid Cozinha cozinha, @PathVariable Long id) {
        service.buscarPorId(id);
        cozinha.setId(id);
        return ResponseEntity.ok(service.salvar(cozinha));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        Cozinha cozinha = service.buscarPorId(id);
        service.remover(cozinha.getId());
    }

















}

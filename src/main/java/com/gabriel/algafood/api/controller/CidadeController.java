package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.domain.exception.EstadoNaoEncontradoException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Estado;
import com.gabriel.algafood.domain.service.CidadeService;
import com.gabriel.algafood.domain.service.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("cidades")
public class CidadeController {

    private CidadeService service;
    private EstadoService estadoService;

    @GetMapping
    public List<Cidade> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade salvar(@RequestBody @Valid Cidade cidade) {
        try {
            return service.salvar(cidade);
        } catch (EstadoNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Cidade editar(@RequestBody @Valid Cidade cidade, @PathVariable Long id) {

        try {
            Cidade cidadeAtual = service.buscarPorId(id);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            return service.salvar(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Cidade cidade = service.buscarPorId(id);
        service.remover(cidade.getId());
        return ResponseEntity.noContent().build();
    }
}

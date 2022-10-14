package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.RestauranteAssembler;
import com.gabriel.algafood.api.model.RestauranteModel;
import com.gabriel.algafood.api.model.request.RestauranteRequest;
import com.gabriel.algafood.core.validation.service.RestauranteService;
import com.gabriel.algafood.domain.exception.CidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Restaurante;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("restaurantes")
public class RestauranteController {

    private RestauranteService service;
    private SmartValidator validator;

    private RestauranteAssembler assembler;

    @GetMapping
    public List<RestauranteModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @GetMapping("/{id}")
    public RestauranteModel buscarPorId(@PathVariable Long id) {
        Restaurante restaurante = service.buscarPorId(id);
        return assembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel salvar(@RequestBody @Valid RestauranteRequest restauranteRequest) {
        try {
            Restaurante restaurante = assembler.toEntity(restauranteRequest);
            return assembler.toModel(service.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteModel editar(@RequestBody @Valid RestauranteRequest restauranteRequest, @PathVariable Long id) {
        try {
            Restaurante restauranteAtual = service.buscarPorId(id);
            assembler.copyToEntity(restauranteRequest, restauranteAtual);
            return assembler.toModel(service.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Restaurante restaurante = service.buscarPorId(id);
        service.remover(restaurante.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id) {
        service.ativar(id);
    }

    @PutMapping("/{id}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        service.inativar(id);
    }

    @PutMapping("/{id}/ativo-ou-inativo")
    public RestauranteModel ativarOuInativar(@PathVariable Long id) {
        return assembler.toModel(service.ativarOuInativar(id));
    }




























}

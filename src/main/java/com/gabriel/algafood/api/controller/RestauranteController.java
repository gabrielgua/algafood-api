package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.RestauranteAssembler;
import com.gabriel.algafood.api.model.RestauranteModel;
import com.gabriel.algafood.api.model.request.RestauranteRequest;
import com.gabriel.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
        } catch (CozinhaNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteModel editar(@RequestBody @Valid RestauranteRequest restauranteRequest, @PathVariable Long id) {
        try {
            Restaurante restaurante = assembler.toEntity(restauranteRequest);

            Restaurante restauranteAtual = service.buscarPorId(id);
            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return assembler.toModel(service.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        Restaurante restaurante = service.buscarPorId(id);
        service.remover(restaurante.getId());
        return ResponseEntity.noContent().build();
    }




























}

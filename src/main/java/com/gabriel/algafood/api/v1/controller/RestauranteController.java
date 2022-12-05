package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.RestauranteApenasNomeAssembler;
import com.gabriel.algafood.api.v1.assembler.RestauranteAssembler;
import com.gabriel.algafood.api.v1.assembler.RestauranteBasicoAssembler;
import com.gabriel.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.gabriel.algafood.api.v1.model.RestauranteBasicoModel;
import com.gabriel.algafood.api.v1.model.RestauranteModel;
import com.gabriel.algafood.api.v1.model.request.RestauranteRequest;
import com.gabriel.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.exception.CidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

    private RestauranteService service;

    private RestauranteAssembler assembler;
    private RestauranteBasicoAssembler basicoAssembler;
    private RestauranteApenasNomeAssembler nomeAssembler;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<RestauranteBasicoModel> listar() {
        return basicoAssembler.toCollectionModel(service.listar());
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @ApiIgnore
    @GetMapping(params = "view=nome", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNome() {
        return nomeAssembler.toCollectionModel(service.listar());
    }


    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteModel buscarPorId(@PathVariable Long id) {
        Restaurante restaurante = service.buscarPorId(id);
        return assembler.toModel(restaurante);
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel salvar(@RequestBody @Valid RestauranteRequest restauranteRequest) {
        try {
            Restaurante restaurante = assembler.toEntity(restauranteRequest);
            return assembler.toModel(service.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteModel editar(@RequestBody @Valid RestauranteRequest restauranteRequest, @PathVariable Long id) {
        try {
            Restaurante restauranteAtual = service.buscarPorId(id);
            assembler.copyToEntity(restauranteRequest, restauranteAtual);
            return assembler.toModel(service.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        Restaurante restaurante = service.buscarPorId(id);
        service.remover(restaurante.getId());
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        service.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{id}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            service.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            service.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{id}/ativo-ou-inativo")
    public void ativarOuInativar(@PathVariable Long id) {
        service.ativarOuInativar(id);
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrirRestaurante(@PathVariable Long id) {
        service.abrirRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fecharRestaurante(@PathVariable Long id) {
        service.fecharRestaurante(id);
        return ResponseEntity.noContent().build();
    }
    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping("/{id}/abertura-ou-fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrirOuFecharRestaurante(@PathVariable Long id) {
        service.abrirOuFecharRestaurante(id);
    }
































}

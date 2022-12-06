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
    @GetMapping(path = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteModel buscarPorId(@PathVariable Long restauranteId) {
        Restaurante restaurante = service.buscarPorId(restauranteId);
        return assembler.toModel(restaurante);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
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

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping(path = "/{restauranteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestauranteModel editar(@RequestBody @Valid RestauranteRequest restauranteRequest, @PathVariable Long restauranteId) {
        try {
            Restaurante restauranteAtual = service.buscarPorId(restauranteId);
            assembler.copyToEntity(restauranteRequest, restauranteAtual);
            return assembler.toModel(service.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{restauranteId}")
    public void remover(@PathVariable Long restauranteId) {
        Restaurante restaurante = service.buscarPorId(restauranteId);
        service.remover(restaurante.getId());
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        service.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        service.inativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            service.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            service.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}/ativo-ou-inativo")
    public void ativarOuInativar(@PathVariable Long restauranteId) {
        service.ativarOuInativar(restauranteId);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrirRestaurante(@PathVariable Long restauranteId) {
        service.abrirRestaurante(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fecharRestaurante(@PathVariable Long restauranteId) {
        service.fecharRestaurante(restauranteId);
        return ResponseEntity.noContent().build();
    }
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{restauranteId}/abertura-ou-fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrirOuFecharRestaurante(@PathVariable Long restauranteId) {
        service.abrirOuFecharRestaurante(restauranteId);
    }
































}

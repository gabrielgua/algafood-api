package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.assembler.UsuarioAssembler;
import com.gabriel.algafood.api.v1.model.UsuarioModel;
import com.gabriel.algafood.api.v1.openapi.controller.RestauranteUsuarioControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController implements RestauranteUsuarioControllerOpenApi {

    private RestauranteService restauranteService;
    private UsuarioAssembler usuarioAssembler;

    private ApiLinks links;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UsuarioModel> listarResponsaveis(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
        var usuariosModel = usuarioAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(links.linkToResponsaveisRestaurante(restauranteId))
                .add(links.linkToRestauranteVincularResponsavel(restauranteId, "vincular"));

        usuariosModel.getContent().forEach(usuario ->
                usuario.add(links.linkToRestauranteDesvincularResponsavel(restauranteId, usuario.getId(), "desvincular")));


        return usuariosModel;
    }
    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> vincularResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.vincularResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desvincularResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desvincularResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}

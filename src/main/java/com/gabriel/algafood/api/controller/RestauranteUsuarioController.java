package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.UsuarioAssembler;
import com.gabriel.algafood.api.model.UsuarioModel;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

    private RestauranteService restauranteService;
    private UsuarioAssembler usuarioAssembler;

    @GetMapping
    public List<UsuarioModel> listarResponsaveis(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
        return usuarioAssembler.toCollectionModel(restaurante.getResponsaveis());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.vincularResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.desvincularResponsavel(restauranteId, usuarioId);
    }
}

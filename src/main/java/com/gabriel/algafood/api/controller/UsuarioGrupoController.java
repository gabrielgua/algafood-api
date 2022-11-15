package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.GrupoAssembler;
import com.gabriel.algafood.api.model.GrupoModel;
import com.gabriel.algafood.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private UsuarioService usuarioService;

    private GrupoAssembler grupoAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GrupoModel> listarGrupos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        return grupoAssembler.toCollectionModel(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    public void vincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.vincularGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    public void desvincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desvincularGrupo(usuarioId, grupoId);
    }


}

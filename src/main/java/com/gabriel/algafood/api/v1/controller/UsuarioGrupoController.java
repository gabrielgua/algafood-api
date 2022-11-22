package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.assembler.GrupoAssembler;
import com.gabriel.algafood.api.v1.model.GrupoModel;
import com.gabriel.algafood.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private UsuarioService usuarioService;
    private GrupoAssembler grupoAssembler;
    private ApiLinks links;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GrupoModel> listarGrupos(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        var gruposModel = grupoAssembler.toCollectionModel(usuario.getGrupos())
                .removeLinks()
                .add(links.linkToUsuarioVincularGrupo(usuarioId, "vincular"));

        gruposModel.getContent().forEach(grupo ->
                grupo.add(links.linkToUsuarioDesvincularGrupo(usuarioId, grupo.getId(), "desvincular")));

        return gruposModel;
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> vincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.vincularGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desvincularGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desvincularGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }


}

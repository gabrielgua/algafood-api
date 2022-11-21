package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.ApiLinks;
import com.gabriel.algafood.api.assembler.PermissaoAssembler;
import com.gabriel.algafood.api.model.PermissaoModel;
import com.gabriel.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.gabriel.algafood.domain.model.Grupo;
import com.gabriel.algafood.domain.service.GrupoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private GrupoService grupoService;
    private PermissaoAssembler permissaoAssembler;
    private ApiLinks links;

    @GetMapping
    public CollectionModel<PermissaoModel> listarPermissoes(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarPorId(grupoId);
        var permissoesModel = permissaoAssembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(links.linkToGrupoPermissoes(grupoId))
                .add(links.linkToGrupoVincularPermissao(grupoId, "vincular"));

        permissoesModel.getContent().forEach(permissao ->
                permissao.add(links.linkToGrupoDesvincularPermissao(grupoId, permissao.getId(), "desvincular")));

        return permissoesModel;
    }

    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> vincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.vincularPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desvincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desvincularPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}

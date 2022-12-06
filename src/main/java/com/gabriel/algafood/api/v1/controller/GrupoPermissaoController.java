package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.assembler.PermissaoAssembler;
import com.gabriel.algafood.api.v1.model.PermissaoModel;
import com.gabriel.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.model.Grupo;
import com.gabriel.algafood.domain.service.GrupoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private GrupoService grupoService;
    private PermissaoAssembler permissaoAssembler;
    private ApiLinks links;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
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

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> vincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.vincularPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desvincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desvincularPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}

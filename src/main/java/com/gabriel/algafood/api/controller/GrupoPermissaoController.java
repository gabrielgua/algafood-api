package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.PermissaoAssembler;
import com.gabriel.algafood.api.model.PermissaoModel;
import com.gabriel.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.gabriel.algafood.domain.model.Grupo;
import com.gabriel.algafood.domain.service.GrupoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private GrupoService grupoService;
    private PermissaoAssembler permissaoAssembler;

    @GetMapping
    public List<PermissaoModel> listarPermissoes(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarPorId(grupoId);
        return permissaoAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    public void vincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.vincularPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    public void desvincularPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
        grupoService.desvincularPermissao(grupoId, permissaoId);
    }
}

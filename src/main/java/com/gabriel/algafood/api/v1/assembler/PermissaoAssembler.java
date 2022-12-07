package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.PermissaoController;
import com.gabriel.algafood.api.v1.model.PermissaoModel;
import com.gabriel.algafood.core.security.SecurityConfig;
import com.gabriel.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissaoAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;
    @Autowired
    private SecurityConfig securityConfig;

    public PermissaoAssembler() {
        super(PermissaoController.class, PermissaoModel.class);
    }

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        var model = super.toCollectionModel(entities);
        if (securityConfig.podeConsultarUsuariosGruposPermissoes()) {
            model.add(links.linkToPermissoes());
        }
        return model;
    }
}

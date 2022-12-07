package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.CozinhaController;
import com.gabriel.algafood.api.v1.model.CozinhaModel;
import com.gabriel.algafood.api.v1.model.request.CozinhaRequest;
import com.gabriel.algafood.core.security.SecurityConfig;
import com.gabriel.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    @Autowired
    private SecurityConfig securityConfig;

    public CozinhaAssembler() {
        super(CozinhaController.class, CozinhaModel.class);
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        var cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        if (securityConfig.podeConsultarCozinhas()) {
            cozinhaModel.add(links.linkToCozinhas("cozinhas"));
        }

        return cozinhaModel;
    }

    @Override
    public CollectionModel<CozinhaModel> toCollectionModel(Iterable<? extends Cozinha> entities) {
        var model = super.toCollectionModel(entities);
        if (securityConfig.podeConsultarCozinhas()) {
            model.add(links.linkToCozinhas("cozinhas"));
        }
        return model;
    }

    public Cozinha toEntity(CozinhaRequest request) {
        return modelMapper.map(request, Cozinha.class);
    }

    public void copyToEntity (CozinhaRequest request, Cozinha cozinha) {
        modelMapper.map(request, cozinha);
    }
}

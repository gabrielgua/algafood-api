package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.RestauranteController;
import com.gabriel.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.gabriel.algafood.core.security.SecurityConfig;
import com.gabriel.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteApenasNomeAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    @Autowired
    private SecurityConfig securityConfig;

    public RestauranteApenasNomeAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        var apenasNome = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, apenasNome);

        if (securityConfig.podeConsultarRestaurantes()) {
            apenasNome.add(links.linkToRestaurantes("restaurantes"));
        }

        return apenasNome;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        var collectionModel = super.toCollectionModel(entities);

        if (securityConfig.podeConsultarRestaurantes()) {
            collectionModel.add(links.linkToRestaurantes("restaurantes"));
        }

        return collectionModel;
    }
}

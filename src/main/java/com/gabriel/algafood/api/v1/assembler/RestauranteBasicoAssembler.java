package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.RestauranteController;
import com.gabriel.algafood.api.v1.model.RestauranteBasicoModel;
import com.gabriel.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public RestauranteBasicoAssembler() {
        super(RestauranteController.class, RestauranteBasicoModel.class);
    }


    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        var restauranteBasicoModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteBasicoModel);

        var cozinhaId = restaurante.getCozinha().getId();

        restauranteBasicoModel.add(links.linkToRestaurantes("restaurantes"));
        restauranteBasicoModel.getCozinha().add(links.linkToCozinha(cozinhaId));

        return restauranteBasicoModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(links.linkToRestaurantes());
    }
}

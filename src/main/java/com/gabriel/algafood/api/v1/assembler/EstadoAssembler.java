package com.gabriel.algafood.api.v1.assembler;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.EstadoController;
import com.gabriel.algafood.api.v1.model.EstadoModel;
import com.gabriel.algafood.api.v1.model.request.EstadoRequest;
import com.gabriel.algafood.core.security.SecurityConfig;
import com.gabriel.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    @Autowired
    private SecurityConfig securityConfig;

    public EstadoAssembler() {
        super(EstadoController.class, EstadoModel.class);
    }

    @Override
    public EstadoModel toModel(Estado estado) {
        var estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);

        if (securityConfig.podeConsultarEstados()) {
            estadoModel.add(links.linkToEstados("estados"));
        }

        return estadoModel;
    }

    public Estado toEntity(EstadoRequest request) {
        return modelMapper.map(request, Estado.class);
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        var model = super.toCollectionModel(entities);
        if (securityConfig.podeConsultarEstados()) {
            model.add(links.linkToEstados());
        }
        return model;
    }

    public void copyToEntity (EstadoRequest request, Estado estado) {
        modelMapper.map(request, estado);
    }
}

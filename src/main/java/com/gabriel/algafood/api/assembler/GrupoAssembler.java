package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.ApiLinks;
import com.gabriel.algafood.api.controller.GrupoController;
import com.gabriel.algafood.api.model.GrupoModel;
import com.gabriel.algafood.api.model.request.GrupoRequest;
import com.gabriel.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GrupoAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public GrupoAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }

    @Override
    public GrupoModel toModel(Grupo grupo) {
        var grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);

        grupoModel.add(links.linkToGrupos("grupos"));
        grupoModel.add(links.linkToGrupoPermissoes(grupo.getId(), "permissoes"));


        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(links.linkToGrupos());
    }

    public Grupo toEntity(GrupoRequest request) {
        return modelMapper.map(request, Grupo.class);
    }

    public void copyToEntity(GrupoRequest request, Grupo grupo) {
        modelMapper.map(request, grupo);
    }
}

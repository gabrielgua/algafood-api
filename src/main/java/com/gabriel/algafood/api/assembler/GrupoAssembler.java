package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.model.GrupoModel;
import com.gabriel.algafood.api.model.request.GrupoRequest;
import com.gabriel.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoModel toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    public Grupo toEntity(GrupoRequest request) {
        return modelMapper.map(request, Grupo.class);
    }

    public List<GrupoModel> toCollectionList(List<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
    }

    public void copyToEntity(GrupoRequest request, Grupo grupo) {
        modelMapper.map(request, grupo);
    }
}

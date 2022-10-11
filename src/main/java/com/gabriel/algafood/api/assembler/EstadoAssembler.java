package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.model.EstadoModel;
import com.gabriel.algafood.api.model.request.EstadoRequest;
import com.gabriel.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModel toModel(Estado estado) {
        return modelMapper.map(estado, EstadoModel.class);
    }

    public Estado toEntity(EstadoRequest request) {
        return modelMapper.map(request, Estado.class);
    }

    public List<EstadoModel> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toModel(estado))
                .collect(Collectors.toList());
    }

    public void copyToEntity (EstadoRequest request, Estado estado) {
        modelMapper.map(request, estado);
    }
}

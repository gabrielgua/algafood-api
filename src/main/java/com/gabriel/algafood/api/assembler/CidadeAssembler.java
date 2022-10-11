package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.model.CidadeModel;
import com.gabriel.algafood.api.model.request.CidadeRequest;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeModel toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeModel.class);
    }

    public Cidade toEntity(CidadeRequest request) {
        return modelMapper.map(request, Cidade.class);
    }

    public List<CidadeModel> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }

    public void copyToEntity (CidadeRequest request, Cidade cidade) {
        // Para evitar Exception do JPA org.hibernate.HibernateException: Tried to change the Identifier of an instance...
        cidade.setEstado(new Estado());
        modelMapper.map(request, cidade);
    }
}

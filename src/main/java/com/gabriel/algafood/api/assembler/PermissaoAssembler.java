package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.model.PermissaoModel;
import com.gabriel.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissaoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public List<PermissaoModel> toCollectionModel(Set<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> toModel(permissao))
                .collect(Collectors.toList());
    }

}

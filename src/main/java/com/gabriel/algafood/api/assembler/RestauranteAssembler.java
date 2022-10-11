package com.gabriel.algafood.api.assembler;

import com.gabriel.algafood.api.model.RestauranteModel;
import com.gabriel.algafood.api.model.request.RestauranteRequest;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteModel toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteModel.class);
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante))
                .collect(Collectors.toList());
    }

    public Restaurante toEntity(RestauranteRequest request) {
        return modelMapper.map(request, Restaurante.class);
    }

    public void copyToEntity(RestauranteRequest request, Restaurante restaurante) {
        // Para evitar Exception do JPA org.hibernate.HibernateException: Tried to change the Identifier of an instance...
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(request, restaurante);
    }

}

package com.gabriel.algafood.core.modelmapper;

import com.gabriel.algafood.api.v1.model.EnderecoModel;
import com.gabriel.algafood.api.v1.model.request.ItemPedidoRequest;
import com.gabriel.algafood.api.v2.model.request.CidadeRequestV2;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Endereco;
import com.gabriel.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(CidadeRequestV2.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

        modelMapper.createTypeMap(ItemPedidoRequest.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);

        enderecoToEnderecoModelTypeMap.<String>addMapping(
                endereco -> endereco.getCidade().getEstado().getNome(),
                (model, value) -> model.getCidade().setEstado(value)
        );


//        enderecoToEnderecoModelTypeMap.<String>addMapping(
//                endereco -> endereco.getCidade().getNome(),
//                (model, value) -> model.setCidade(value)
//        );
//
//        enderecoToEnderecoModelTypeMap.<String>addMapping(
//                endereco -> endereco.getCidade().getEstado().getNome(),
//                (model, value) -> model.setEstado(value)
//        );


        return modelMapper;
    }
}

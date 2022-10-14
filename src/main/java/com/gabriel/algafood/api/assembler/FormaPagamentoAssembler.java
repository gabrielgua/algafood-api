package com.gabriel.algafood.api.assembler;


import com.gabriel.algafood.api.model.FormaPagamentoModel;
import com.gabriel.algafood.api.model.request.FormaPagamentoRequest;
import com.gabriel.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public FormaPagamento toEntity(FormaPagamentoRequest request) {
        return modelMapper.map(request, FormaPagamento.class);
    }

    public List<FormaPagamentoModel> toCollectionList(List<FormaPagamento> formasPagamento) {
        return formasPagamento.stream()
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }

    public void copyToEntity(FormaPagamentoRequest request, FormaPagamento formaPagamento) {
        modelMapper.map(request, formaPagamento);
    }
}

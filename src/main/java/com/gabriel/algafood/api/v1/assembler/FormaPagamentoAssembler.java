package com.gabriel.algafood.api.v1.assembler;


import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.controller.FormaPagamentoController;
import com.gabriel.algafood.api.v1.model.FormaPagamentoModel;
import com.gabriel.algafood.api.v1.model.request.FormaPagamentoRequest;
import com.gabriel.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiLinks links;

    public FormaPagamentoAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoModel.class);
    }

    @Override
    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        var formaPagamentoModel = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, formaPagamentoModel);

        formaPagamentoModel.add(links.linkToFormaPagamentos("formasPagamento"));
        return formaPagamentoModel;
    }

    @Override
    public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(links.linkToFormaPagamentos());
    }

    public FormaPagamento toEntity(FormaPagamentoRequest request) {
        return modelMapper.map(request, FormaPagamento.class);
    }

    public void copyToEntity(FormaPagamentoRequest request, FormaPagamento formaPagamento) {

        modelMapper.map(request, formaPagamento);
    }
}

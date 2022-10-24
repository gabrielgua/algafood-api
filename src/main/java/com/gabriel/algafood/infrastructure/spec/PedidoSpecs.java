package com.gabriel.algafood.infrastructure.spec;

import com.gabriel.algafood.domain.model.Pedido;
import com.gabriel.algafood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

    public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {
        return (root, query, criteriaBuilder) -> {
            root.fetch("restaurante").fetch("cozinha");
            root.fetch("cliente");

            var predicates = new ArrayList<Predicate>();

            if (filter.getClienteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("cliente"), filter.getClienteId()));
            }

            if (filter.getRestauranteId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("restaurante"), filter.getRestauranteId()));
            }

            if (filter.getDataCriacaoInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
            }

            if (filter.getDataCriacaoFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

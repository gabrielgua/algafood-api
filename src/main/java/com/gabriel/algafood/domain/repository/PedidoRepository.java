package com.gabriel.algafood.domain.repository;

import com.gabriel.algafood.domain.model.Pedido;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{
}

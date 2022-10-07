package com.gabriel.algafood.domain.repository;

import com.gabriel.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r join r.cozinha")
    List<Restaurante> findAll();

}

package com.gabriel.algafood.domain.repository;

import com.gabriel.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
}

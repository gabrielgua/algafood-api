package com.gabriel.algafood.domain.repository;

import com.gabriel.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("SELECT MAX(dataAtualizacao) from FormaPagamento")
    OffsetDateTime getUltimaDataAtualizacao();

    @Query("SELECT MAX(dataAtualizacao) from FormaPagamento WHERE id = :id")
    OffsetDateTime getUltimaDataAtualizacaoById(Long id);
}

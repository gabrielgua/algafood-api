package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.filter.VendaDiariaFilter;
import com.gabriel.algafood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

}

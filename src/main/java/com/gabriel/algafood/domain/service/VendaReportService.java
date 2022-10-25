package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}

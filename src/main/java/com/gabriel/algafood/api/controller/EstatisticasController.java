package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.domain.filter.VendaDiariaFilter;
import com.gabriel.algafood.domain.model.dto.VendaDiaria;
import com.gabriel.algafood.domain.service.VendaQueryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("estatisticas")
public class EstatisticasController {

    private VendaQueryService vendaQueryService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria>  consultarVendasDiarias(VendaDiariaFilter filtro) {
        return vendaQueryService.consultarVendasDiarias(filtro);
    }
}

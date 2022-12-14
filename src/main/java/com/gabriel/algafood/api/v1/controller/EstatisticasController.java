package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.filter.VendaDiariaFilter;
import com.gabriel.algafood.domain.model.dto.VendaDiaria;
import com.gabriel.algafood.domain.service.VendaQueryService;
import com.gabriel.algafood.domain.service.VendaReportService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

    private VendaQueryService vendaQueryService;
    private VendaReportService vendaReportService;
    private ApiLinks links;


    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping
    public EstatisticaModel mostrarLinks() {
        var estatisticasModel = new EstatisticaModel();
        estatisticasModel.add(links.linkToEstatisticasVendasDiarias("vendas-diarias"));
        return estatisticasModel;
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria>  consultarVendasDiarias(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

    private static class EstatisticaModel extends RepresentationModel<EstatisticaModel> {
    }
}

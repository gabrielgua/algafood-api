package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.openapi.controller.StatusPedidoControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.service.StatusPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/pedidos/{codigoPedido}")
public class StatusPedidoController  implements StatusPedidoControllerOpenApi {

    private StatusPedidoService statusService;

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmarPedido(@PathVariable String codigoPedido) {
        statusService.confirmar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancelarPedido(@PathVariable String codigoPedido) {
        statusService.cancelar(codigoPedido);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirmarEntrega(@PathVariable String codigoPedido) {
        statusService.confirmarEntrega(codigoPedido);
        return ResponseEntity.noContent().build();
    }
}

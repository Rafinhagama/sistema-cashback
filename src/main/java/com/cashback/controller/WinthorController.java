package com.cashback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cashback.dto.WinthorPedidoResponse;
import com.cashback.service.WinthorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/winthor")
public class WinthorController {

    private final WinthorService winthorService;

    public WinthorController(WinthorService winthorService) {
        this.winthorService = winthorService;
    }

    @GetMapping("/pedidos/{numeroPedido}")
    public ResponseEntity<WinthorPedidoResponse> buscarPedido(
            @PathVariable String numeroPedido
    ) {
        return ResponseEntity.ok(winthorService.buscarPedido(numeroPedido));
    }
}
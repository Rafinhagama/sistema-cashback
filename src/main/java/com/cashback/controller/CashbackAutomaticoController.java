package com.cashback.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cashback.dto.CashbackAutomaticoRequest;
import com.cashback.dto.CashbackAutomaticoResponse;
import com.cashback.dto.PedidoAutomaticoDTO;
import com.cashback.service.CashbackAutomaticoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cashback/automatico")
public class CashbackAutomaticoController {

    private final CashbackAutomaticoService cashbackAutomaticoService;

    public CashbackAutomaticoController(CashbackAutomaticoService cashbackAutomaticoService) {
        this.cashbackAutomaticoService = cashbackAutomaticoService;
    }

    @GetMapping("/simular")
    public ResponseEntity<List<PedidoAutomaticoDTO>> simular(
            @RequestParam(required = false) BigDecimal percentualCashback,
            @RequestParam(required = false) Integer diasUltimoPedido
    ) {
        return ResponseEntity.ok(
                cashbackAutomaticoService.simular(percentualCashback, diasUltimoPedido)
        );
    }

    @PostMapping("/processar")
    public ResponseEntity<CashbackAutomaticoResponse> processar(
            @RequestBody CashbackAutomaticoRequest request
    ) {
        return ResponseEntity.ok(
                cashbackAutomaticoService.processar(request)
        );
    }
}
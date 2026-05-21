package com.cashback.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cashback.dto.GerarCashbackRequest;
import com.cashback.dto.UsarCashbackRequest;
import com.cashback.dto.UsarCashbackResponse;
import com.cashback.model.CashbackTransaction;
import com.cashback.model.CashbackWallet;
import com.cashback.service.CashbackService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/cashback")
public class CashbackController {

    private final CashbackService cashbackService;

    public CashbackController(CashbackService cashbackService) {
        this.cashbackService = cashbackService;
    }

    @PostMapping("/gerar")
    public ResponseEntity<CashbackTransaction> gerarCashback(
            @RequestBody GerarCashbackRequest request
    ) {
        return ResponseEntity.ok(cashbackService.gerarCashback(request));
    }

    @PostMapping("/usar")
    public ResponseEntity<UsarCashbackResponse> usarCashback(
            @RequestBody UsarCashbackRequest request
    ) {
        return ResponseEntity.ok(cashbackService.usarCashback(request));
    }

    @PostMapping("/liberar-pendentes")
    public ResponseEntity<Void> liberarPendentes() {
        cashbackService.liberarCashbacksPendentes();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/expirar")
    public ResponseEntity<Void> expirarCashbacks() {
        cashbackService.expirarCashbacks();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancelar/{pedidoId}")
    public ResponseEntity<Void> cancelarCashback(
            @PathVariable String pedidoId
    ) {
        cashbackService.cancelarCashbackPorPedido(pedidoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/saldo/{clienteId}")
    public ResponseEntity<CashbackWallet> buscarSaldo(
            @PathVariable String clienteId
    ) {
        return ResponseEntity.ok(cashbackService.buscarSaldo(clienteId));
    }

    @GetMapping("/historico/{clienteId}")
    public ResponseEntity<List<CashbackTransaction>> buscarHistorico(
            @PathVariable String clienteId
    ) {
        return ResponseEntity.ok(cashbackService.buscarHistorico(clienteId));
    }
}
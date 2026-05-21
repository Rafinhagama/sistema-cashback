package com.cashback.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cashback_wallet")
public class CashbackWallet {

    @Id
    private String id;

    private String clienteId;

    private BigDecimal saldoDisponivel = BigDecimal.ZERO;
    private BigDecimal saldoPendente = BigDecimal.ZERO;
    private BigDecimal saldoUtilizado = BigDecimal.ZERO;
    private BigDecimal saldoExpirado = BigDecimal.ZERO;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public BigDecimal getSaldoPendente() {
        return saldoPendente;
    }

    public void setSaldoPendente(BigDecimal saldoPendente) {
        this.saldoPendente = saldoPendente;
    }

    public BigDecimal getSaldoUtilizado() {
        return saldoUtilizado;
    }

    public void setSaldoUtilizado(BigDecimal saldoUtilizado) {
        this.saldoUtilizado = saldoUtilizado;
    }

    public BigDecimal getSaldoExpirado() {
        return saldoExpirado;
    }

    public void setSaldoExpirado(BigDecimal saldoExpirado) {
        this.saldoExpirado = saldoExpirado;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
package com.cashback.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cashback.enums.CashbackStatus;
import com.cashback.enums.CashbackTipo;

@Document(collection = "cashback_transaction")
public class CashbackTransaction {

    @Id
    private String id;

    private String clienteId;
    private String pedidoId;

    private CashbackTipo tipo;
    private CashbackStatus status;

    private BigDecimal valorBase;
    private BigDecimal percentual;
    private BigDecimal valorCashback;

    private LocalDateTime criadoEm;
    private LocalDateTime liberacaoEm;
    private LocalDateTime expiracaoEm;

    private String descricao;

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

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public CashbackTipo getTipo() {
        return tipo;
    }

    public void setTipo(CashbackTipo tipo) {
        this.tipo = tipo;
    }

    public CashbackStatus getStatus() {
        return status;
    }

    public void setStatus(CashbackStatus status) {
        this.status = status;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

    public BigDecimal getValorCashback() {
        return valorCashback;
    }

    public void setValorCashback(BigDecimal valorCashback) {
        this.valorCashback = valorCashback;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getLiberacaoEm() {
        return liberacaoEm;
    }

    public void setLiberacaoEm(LocalDateTime liberacaoEm) {
        this.liberacaoEm = liberacaoEm;
    }

    public LocalDateTime getExpiracaoEm() {
        return expiracaoEm;
    }

    public void setExpiracaoEm(LocalDateTime expiracaoEm) {
        this.expiracaoEm = expiracaoEm;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
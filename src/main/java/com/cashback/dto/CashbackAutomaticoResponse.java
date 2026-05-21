package com.cashback.dto;

import java.math.BigDecimal;
import java.util.List;

public class CashbackAutomaticoResponse {

    private Integer clientesAnalisados;
    private Integer clientesElegiveis;
    private Integer cashbacksGerados;
    private Integer cashbacksIgnorados;
    private BigDecimal valorTotalCashback;
    private List<PedidoAutomaticoDTO> pedidos;

    public Integer getClientesAnalisados() {
        return clientesAnalisados;
    }

    public void setClientesAnalisados(Integer clientesAnalisados) {
        this.clientesAnalisados = clientesAnalisados;
    }

    public Integer getClientesElegiveis() {
        return clientesElegiveis;
    }

    public void setClientesElegiveis(Integer clientesElegiveis) {
        this.clientesElegiveis = clientesElegiveis;
    }

    public Integer getCashbacksGerados() {
        return cashbacksGerados;
    }

    public void setCashbacksGerados(Integer cashbacksGerados) {
        this.cashbacksGerados = cashbacksGerados;
    }

    public Integer getCashbacksIgnorados() {
        return cashbacksIgnorados;
    }

    public void setCashbacksIgnorados(Integer cashbacksIgnorados) {
        this.cashbacksIgnorados = cashbacksIgnorados;
    }

    public BigDecimal getValorTotalCashback() {
        return valorTotalCashback;
    }

    public void setValorTotalCashback(BigDecimal valorTotalCashback) {
        this.valorTotalCashback = valorTotalCashback;
    }

    public List<PedidoAutomaticoDTO> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoAutomaticoDTO> pedidos) {
        this.pedidos = pedidos;
    }
}

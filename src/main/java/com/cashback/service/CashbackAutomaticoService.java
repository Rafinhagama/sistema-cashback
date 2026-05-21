package com.cashback.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cashback.dto.CashbackAutomaticoRequest;
import com.cashback.dto.CashbackAutomaticoResponse;
import com.cashback.dto.GerarCashbackRequest;
import com.cashback.dto.PedidoAutomaticoDTO;

@Service
public class CashbackAutomaticoService {

    private final CashbackService cashbackService;

    public CashbackAutomaticoService(CashbackService cashbackService) {
        this.cashbackService = cashbackService;
    }

    public List<PedidoAutomaticoDTO> simular(BigDecimal percentualCashback, Integer diasUltimoPedido) {

        BigDecimal percentual = percentualCashback != null
                ? percentualCashback
                : BigDecimal.valueOf(5);

        Integer limiteDias = diasUltimoPedido != null
                ? diasUltimoPedido
                : 15;

        List<PedidoAutomaticoDTO> pedidos = buscarPedidosParaAnalise();

        for (PedidoAutomaticoDTO pedido : pedidos) {
            aplicarRegra(pedido, percentual, limiteDias);
        }

        return pedidos;
    }

    public CashbackAutomaticoResponse processar(CashbackAutomaticoRequest request) {

        BigDecimal percentual = request.getPercentualCashback() != null
                ? request.getPercentualCashback()
                : BigDecimal.valueOf(5);

        Integer limiteDias = request.getDiasUltimoPedido() != null
                ? request.getDiasUltimoPedido()
                : 15;

        List<PedidoAutomaticoDTO> pedidos = simular(percentual, limiteDias);

        int analisados = pedidos.size();
        int elegiveis = 0;
        int gerados = 0;
        int ignorados = 0;

        BigDecimal valorTotalCashback = BigDecimal.ZERO;

        for (PedidoAutomaticoDTO pedido : pedidos) {

            if (Boolean.TRUE.equals(pedido.getElegivel())) {
                elegiveis++;

                try {
                	GerarCashbackRequest gerarRequest = new GerarCashbackRequest();
                	gerarRequest.setClienteId(pedido.getClienteId());
                	gerarRequest.setPedidoId(pedido.getPedidoId());
                	gerarRequest.setValorPedido(pedido.getValorPedido());
                	gerarRequest.setValorPagoEmDinheiro(pedido.getValorPedido());
                	gerarRequest.setPercentualCashback(percentual);
                	gerarRequest.setAprovacaoInstantanea(true);

                    cashbackService.gerarCashback(gerarRequest);

                    gerados++;
                    valorTotalCashback = valorTotalCashback.add(pedido.getValorCashback());
                    pedido.setMensagem("Cashback automático gerado com sucesso.");

                } catch (Exception e) {
                    ignorados++;
                    pedido.setMensagem("Cashback não gerado: " + e.getMessage());
                }

            } else {
                ignorados++;
            }
        }

        CashbackAutomaticoResponse response = new CashbackAutomaticoResponse();
        response.setClientesAnalisados(analisados);
        response.setClientesElegiveis(elegiveis);
        response.setCashbacksGerados(gerados);
        response.setCashbacksIgnorados(ignorados);
        response.setValorTotalCashback(valorTotalCashback);
        response.setPedidos(pedidos);

        return response;
    }

    private void aplicarRegra(
            PedidoAutomaticoDTO pedido,
            BigDecimal percentual,
            Integer limiteDias
    ) {

        boolean pedidoPagoNoPrazo = Boolean.TRUE.equals(pedido.getPagoDentroPrazo());

        boolean ultimoPedidoMenorQueLimite = pedido.getDiasUltimoPedido() != null
                && pedido.getDiasUltimoPedido() < limiteDias;

        boolean elegivel = pedidoPagoNoPrazo && ultimoPedidoMenorQueLimite;

        pedido.setElegivel(elegivel);

        if (elegivel) {
            BigDecimal valorCashback = pedido.getValorPedido()
                    .multiply(percentual)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            pedido.setValorCashback(valorCashback);
            pedido.setMensagem("Cliente elegível para cashback automático.");
        } else {
            pedido.setValorCashback(BigDecimal.ZERO);

            if (!pedidoPagoNoPrazo) {
                pedido.setMensagem("Pedido não foi pago dentro do prazo.");
            } else if (!ultimoPedidoMenorQueLimite) {
                pedido.setMensagem("Último pedido do cliente não foi feito em menos de " + limiteDias + " dias.");
            } else {
                pedido.setMensagem("Cliente não elegível.");
            }
        }
    }

    /*
     * Por enquanto está com dados simulados.
     * Depois, esse método será substituído por uma consulta real ao Winthor.
     */
    private List<PedidoAutomaticoDTO> buscarPedidosParaAnalise() {

        List<PedidoAutomaticoDTO> pedidos = new ArrayList<>();

        pedidos.add(criarPedido(
                "CLI-001",
                "PED-3001",
                new BigDecimal("850.00"),
                true,
                8,
                "PAGO"
        ));

        pedidos.add(criarPedido(
                "CLI-002",
                "PED-3002",
                new BigDecimal("420.00"),
                false,
                6,
                "PAGO"
        ));

        pedidos.add(criarPedido(
                "CLI-003",
                "PED-3003",
                new BigDecimal("1270.00"),
                true,
                21,
                "PAGO"
        ));

        pedidos.add(criarPedido(
                "CLI-004",
                "PED-3004",
                new BigDecimal("690.00"),
                true,
                3,
                "PAGO"                
        ));
        
        pedidos.add(criarPedido(
                "CLI-005",
                "PED-30324",
                new BigDecimal("1000.00"),
                true,
                16,
                "PAGO"                
        ));

        return pedidos;
    }

    private PedidoAutomaticoDTO criarPedido(
            String clienteId,
            String pedidoId,
            BigDecimal valorPedido,
            Boolean pagoDentroPrazo,
            Integer diasUltimoPedido,
            String statusPedido
    ) {

        PedidoAutomaticoDTO pedido = new PedidoAutomaticoDTO();
        pedido.setClienteId(clienteId);
        pedido.setPedidoId(pedidoId);
        pedido.setValorPedido(valorPedido);
        pedido.setPagoDentroPrazo(pagoDentroPrazo);
        pedido.setDiasUltimoPedido(diasUltimoPedido);
        pedido.setStatusPedido(statusPedido);

        return pedido;
    }
}
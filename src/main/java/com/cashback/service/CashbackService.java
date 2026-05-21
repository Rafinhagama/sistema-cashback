package com.cashback.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cashback.dto.GerarCashbackRequest;
import com.cashback.dto.UsarCashbackRequest;
import com.cashback.dto.UsarCashbackResponse;
import com.cashback.enums.CashbackStatus;
import com.cashback.enums.CashbackTipo;
import com.cashback.model.CashbackTransaction;
import com.cashback.model.CashbackWallet;
import com.cashback.repository.CashbackTransactionRepository;
import com.cashback.repository.CashbackWalletRepository;


@Service
public class CashbackService {

    private final CashbackWalletRepository walletRepository;
    private final CashbackTransactionRepository transactionRepository;

    public CashbackService(
            CashbackWalletRepository walletRepository,
            CashbackTransactionRepository transactionRepository
    ) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public CashbackTransaction gerarCashback(GerarCashbackRequest request) {

        if (request.getValorPagoEmDinheiro().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor pago em dinheiro deve ser maior que zero.");
        }

        boolean cashbackJaGerado = transactionRepository
                .findByPedidoIdAndTipo(request.getPedidoId(), CashbackTipo.CREDITO)
                .isPresent();

        if (cashbackJaGerado) {
            throw new RuntimeException("Cashback já gerado para este pedido.");
        }

        BigDecimal valorCashback = calcularCashback(
                request.getValorPagoEmDinheiro(),
                request.getPercentualCashback()
        );

        CashbackWallet wallet = buscarOuCriarCarteira(request.getClienteId());

        boolean aprovacaoInstantanea = Boolean.TRUE.equals(request.getAprovacaoInstantanea());

        if (aprovacaoInstantanea) {
            wallet.setSaldoDisponivel(
                    wallet.getSaldoDisponivel().add(valorCashback)
            );
        } else {
            wallet.setSaldoPendente(
                    wallet.getSaldoPendente().add(valorCashback)
            );
        }

        wallet.setAtualizadoEm(LocalDateTime.now());
        walletRepository.save(wallet);

        CashbackTransaction transaction = new CashbackTransaction();
        transaction.setClienteId(request.getClienteId());
        transaction.setPedidoId(request.getPedidoId());
        transaction.setTipo(CashbackTipo.CREDITO);

        if (aprovacaoInstantanea) {
            transaction.setStatus(CashbackStatus.DISPONIVEL);
            transaction.setLiberacaoEm(LocalDateTime.now());
            transaction.setDescricao("Cashback automático aprovado pelo pedido " + request.getPedidoId());
        } else {
            transaction.setStatus(CashbackStatus.PENDENTE);
            transaction.setLiberacaoEm(LocalDateTime.now().plusSeconds(1));
            transaction.setDescricao("Cashback gerado pelo pedido " + request.getPedidoId());
        }

        transaction.setValorBase(request.getValorPagoEmDinheiro());
        transaction.setPercentual(request.getPercentualCashback());
        transaction.setValorCashback(valorCashback);
        transaction.setCriadoEm(LocalDateTime.now());
        transaction.setExpiracaoEm(LocalDateTime.now().plusDays(90));

        return transactionRepository.save(transaction);
    }

    public UsarCashbackResponse usarCashback(UsarCashbackRequest request) {

        CashbackWallet wallet = buscarOuCriarCarteira(request.getClienteId());

        if (wallet.getSaldoDisponivel().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Cliente não possui cashback disponível.");
        }

        BigDecimal limiteUso = request.getValorPedido()
                .multiply(request.getPercentualMaximoUso())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal cashbackUtilizado = wallet.getSaldoDisponivel().min(limiteUso);

        BigDecimal valorFinal = request.getValorPedido().subtract(cashbackUtilizado);

        wallet.setSaldoDisponivel(
                wallet.getSaldoDisponivel().subtract(cashbackUtilizado)
        );

        wallet.setSaldoUtilizado(
                wallet.getSaldoUtilizado().add(cashbackUtilizado)
        );

        wallet.setAtualizadoEm(LocalDateTime.now());
        walletRepository.save(wallet);

        CashbackTransaction transaction = new CashbackTransaction();
        transaction.setClienteId(request.getClienteId());
        transaction.setPedidoId(request.getPedidoId());
        transaction.setTipo(CashbackTipo.DEBITO);
        transaction.setStatus(CashbackStatus.USADO);
        transaction.setValorBase(request.getValorPedido());
        transaction.setPercentual(request.getPercentualMaximoUso());
        transaction.setValorCashback(cashbackUtilizado);
        transaction.setCriadoEm(LocalDateTime.now());
        transaction.setDescricao("Cashback utilizado no pedido " + request.getPedidoId());

        transactionRepository.save(transaction);

        UsarCashbackResponse response = new UsarCashbackResponse();
        response.setValorOriginal(request.getValorPedido());
        response.setCashbackUtilizado(cashbackUtilizado);
        response.setValorFinal(valorFinal);

        return response;
    }

    public void liberarCashbacksPendentes() {

        List<CashbackTransaction> pendentes = transactionRepository
                .findByStatusAndLiberacaoEmBefore(
                        CashbackStatus.PENDENTE,
                        LocalDateTime.now()
                );

        for (CashbackTransaction transaction : pendentes) {

            CashbackWallet wallet = buscarOuCriarCarteira(transaction.getClienteId());

            wallet.setSaldoPendente(
                    wallet.getSaldoPendente().subtract(transaction.getValorCashback())
            );

            wallet.setSaldoDisponivel(
                    wallet.getSaldoDisponivel().add(transaction.getValorCashback())
            );

            wallet.setAtualizadoEm(LocalDateTime.now());

            transaction.setStatus(CashbackStatus.DISPONIVEL);

            walletRepository.save(wallet);
            transactionRepository.save(transaction);
        }
    }

    public void expirarCashbacks() {

        List<CashbackTransaction> disponiveis = transactionRepository
                .findByStatusAndExpiracaoEmBefore(
                        CashbackStatus.DISPONIVEL,
                        LocalDateTime.now()
                );

        for (CashbackTransaction transaction : disponiveis) {

            CashbackWallet wallet = buscarOuCriarCarteira(transaction.getClienteId());

            wallet.setSaldoDisponivel(
                    wallet.getSaldoDisponivel().subtract(transaction.getValorCashback())
            );

            wallet.setSaldoExpirado(
                    wallet.getSaldoExpirado().add(transaction.getValorCashback())
            );

            wallet.setAtualizadoEm(LocalDateTime.now());

            transaction.setStatus(CashbackStatus.EXPIRADO);
            transaction.setTipo(CashbackTipo.EXPIRACAO);

            walletRepository.save(wallet);
            transactionRepository.save(transaction);
        }
    }

    public void cancelarCashbackPorPedido(String pedidoId) {

        CashbackTransaction transaction = transactionRepository
                .findByPedidoIdAndTipo(pedidoId, CashbackTipo.CREDITO)
                .orElseThrow(() -> new RuntimeException("Cashback não encontrado para este pedido."));

        CashbackWallet wallet = buscarOuCriarCarteira(transaction.getClienteId());

        if (transaction.getStatus() == CashbackStatus.PENDENTE) {
            wallet.setSaldoPendente(
                    wallet.getSaldoPendente().subtract(transaction.getValorCashback())
            );
        }

        if (transaction.getStatus() == CashbackStatus.DISPONIVEL) {
            wallet.setSaldoDisponivel(
                    wallet.getSaldoDisponivel().subtract(transaction.getValorCashback())
            );
        }

        transaction.setStatus(CashbackStatus.CANCELADO);
        wallet.setAtualizadoEm(LocalDateTime.now());

        walletRepository.save(wallet);
        transactionRepository.save(transaction);
    }

    public CashbackWallet buscarSaldo(String clienteId) {
        return buscarOuCriarCarteira(clienteId);
    }

    public List<CashbackTransaction> buscarHistorico(String clienteId) {
        return transactionRepository.findByClienteIdOrderByCriadoEmDesc(clienteId);
    }

    private BigDecimal calcularCashback(BigDecimal valorBase, BigDecimal percentual) {
        return valorBase
                .multiply(percentual)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    private CashbackWallet buscarOuCriarCarteira(String clienteId) {

        return walletRepository.findByClienteId(clienteId)
                .orElseGet(() -> {
                    CashbackWallet wallet = new CashbackWallet();
                    wallet.setClienteId(clienteId);
                    wallet.setCriadoEm(LocalDateTime.now());
                    wallet.setAtualizadoEm(LocalDateTime.now());
                    return walletRepository.save(wallet);
                });
    }
}
package com.cashback.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cashback.enums.CashbackStatus;
import com.cashback.enums.CashbackTipo;
import com.cashback.model.CashbackTransaction;


public interface CashbackTransactionRepository extends MongoRepository<CashbackTransaction, String> {

    List<CashbackTransaction> findByClienteIdOrderByCriadoEmDesc(String clienteId);

    List<CashbackTransaction> findByStatusAndLiberacaoEmBefore(
            CashbackStatus status,
            LocalDateTime data
    );

    List<CashbackTransaction> findByStatusAndExpiracaoEmBefore(
            CashbackStatus status,
            LocalDateTime data
    );

    Optional<CashbackTransaction> findByPedidoIdAndTipo(
            String pedidoId,
            CashbackTipo tipo
    );
}
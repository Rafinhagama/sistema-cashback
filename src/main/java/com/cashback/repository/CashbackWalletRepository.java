package com.cashback.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cashback.model.CashbackWallet;

public interface CashbackWalletRepository extends MongoRepository<CashbackWallet, String> {

    Optional<CashbackWallet> findByClienteId(String clienteId);
}
package com.crypto.CoinFolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crypto.CoinFolio.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long>{
    Wallet findByUserId(Long userId);
}

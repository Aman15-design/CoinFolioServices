package com.crypto.CoinFolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crypto.CoinFolio.model.Coin;

@Repository
public interface CoinRepository extends JpaRepository<Coin,String>{
    
}

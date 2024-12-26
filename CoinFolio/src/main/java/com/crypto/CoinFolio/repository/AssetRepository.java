package com.crypto.CoinFolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.CoinFolio.model.Asset;

public interface AssetRepository extends JpaRepository<Asset,Long> {
    List<Asset> findByUserId(Long userId);
    Asset findByUserIdAndCoinId(Long userId,Long coinId);
}

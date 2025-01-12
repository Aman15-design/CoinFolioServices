package com.crypto.CoinFolio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crypto.CoinFolio.model.Asset;
import com.crypto.CoinFolio.model.Coin;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.repository.AssetRepository;
import com.crypto.CoinFolio.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService{

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, Double quantity) {
        Asset asset = new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyPrice(coin.getCurrentPrice());
        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {
        return assetRepository.findById(assetId).orElseThrow(() -> new Exception("Asset Not Found"));
    }

    @Override
    public Asset getAssetByUserId(Long userId, long assetId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAssetByUserId'");
    }

    @Override
    public List<Asset> getUserAsset(Long userId) {
        return assetRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(long assetId, double quantity) {
        Asset oldAsset;
        try {
            oldAsset = getAssetById(assetId);
            oldAsset.setQuantity(quantity + oldAsset.getQuantity());
            return assetRepository.save(oldAsset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepository.findByUserIdAndCoinId(userId, userId);
    }

    @Override
    public void deleteAsset(long assetId) {
       assetRepository.deleteById(assetId);
    }
    
}

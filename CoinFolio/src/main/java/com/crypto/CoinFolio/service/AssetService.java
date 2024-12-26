package com.crypto.CoinFolio.service;

import java.util.List;

import com.crypto.CoinFolio.model.Asset;
import com.crypto.CoinFolio.model.Coin;
import com.crypto.CoinFolio.model.User;

public interface AssetService {
    Asset createAsset(User user,Coin coin,Double quantity);
    Asset getAssetById(Long assetId);
    Asset getAssetByUserId(Long userId, long assetId);
    List<Asset> getUserAsset(Long userId);
    Asset updateAsset(long assetId, double quantity);
    Asset findAssetByUserIdAndCoinId(Long userId,String coinId);
    void deleteAsset(long assetId);
}

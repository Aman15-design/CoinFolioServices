package com.crypto.CoinFolio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crypto.CoinFolio.model.Coin;

@Service
public interface CoinService {
    List<Coin> getCoinList(int page, String apiKey);

    String getMarketChart(String coinId, int days, String apiKey) throws Exception;

    String getCoinDetails(String coinId, String apiKey) throws Exception;

    Coin findById(String coinId) throws Exception;

    String searchCoin(String keyWord, String apiKey);

    String getTop50CoinsByMarketCapRank(String apiKey);

    String getTrendingCoins(String apiKey);

}

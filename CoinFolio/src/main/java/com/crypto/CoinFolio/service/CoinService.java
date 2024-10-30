package com.crypto.CoinFolio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crypto.CoinFolio.model.Coin;

@Service
public interface CoinService {
    List<Coin> getCoinList(int page);

    String getMarketChart(String coinId, int days) throws Exception;

    String getCoinDetails(String coinId) throws Exception;

    Coin findById(String coinId) throws Exception;

    String searchCoin(String keyWord);

    String getTop50CoinsByMarketCapRank();

    String getTrendingCoins();

}

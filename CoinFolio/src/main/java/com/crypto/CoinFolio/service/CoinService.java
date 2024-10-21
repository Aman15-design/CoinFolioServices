package com.crypto.CoinFolio.service;

import java.util.List;

import com.crypto.CoinFolio.model.Coin;

public interface CoinService {
    List<Coin> getCoinList(int page);

    String getMarketChart(String coinId, int days);

    String getCoinDetails(String coinId);

    Coin findById(String coinId);

    String searchCoin(String keyWord);

    String getTop50CoinsByMarketCapRank();

    String getTradingCoins();

}

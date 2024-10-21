package com.crypto.CoinFolio.service.impl;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.Unirest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

import com.crypto.CoinFolio.model.Coin;
import com.crypto.CoinFolio.repository.CoinRepository;
import com.crypto.CoinFolio.service.CoinService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoinServiceImpl implements CoinService{

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;

   @Override
public List<Coin> getCoinList(int page) {
    kong.unirest.HttpResponse<String> response = Unirest.get("https://api.coingecko.com/api/v3/coins/markets")
            .header("accept", "application/json")
            .header("x-cg-demo-api-key", "CG-NKcvLJG6zrc5LbPijBsFV9jL")
            .queryString("page", page) // Add pagination if supported by the API
            .queryString("per_page", 10) // Adjust per_page value as needed
            .asString();

    // Check if the response status is 200 OK
    if (response.getStatus() != 200) {
        throw new RuntimeException("Failed to get data: " + response.getStatusText());
    }

    // Use ObjectMapper to convert JSON string to List<Coin>
    ObjectMapper objectMapper = new ObjectMapper();
    try {
        return objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {});
    } catch (JsonProcessingException e) {
        // Log the error instead of printing stack trace
        Logger logger = (Logger) LoggerFactory.getLogger(getClass());
        logger.error("Error parsing JSON response", e);
    }

    return Collections.emptyList(); // Return an empty list in case of failure
}


    @Override
    public String getMarketChart(String coinId, int days) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMarketChart'");
    }

    @Override
    public String getCoinDetails(String coinId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCoinDetails'");
    }

    @Override
    public Coin findById(String coinId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public String searchCoin(String keyWord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchCoin'");
    }

    @Override
    public String getTop50CoinsByMarketCapRank() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTop50CoinsByMarketCapRank'");
    }

    @Override
    public String getTradingCoins() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTradingCoins'");
    }
    
}

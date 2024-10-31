package com.crypto.CoinFolio.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.Unirest;

import com.crypto.CoinFolio.model.Coin;
import com.crypto.CoinFolio.repository.CoinRepository;
import com.crypto.CoinFolio.service.CoinService;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Coin> getCoinList(int page, String apiKey) {
        kong.unirest.HttpResponse<String> response = Unirest
                .get("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd")
                .header("accept", "application/json")
                .header("x-cg-demo-api-key", apiKey) //CG-NKcvLJG6zrc5LbPijBsFV9jL
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
            return objectMapper.readValue(response.getBody(), new TypeReference<List<Coin>>() {
            });
        } catch (JsonProcessingException e) {
            // Log the error instead of printing stack trace
            Logger logger = (Logger) LoggerFactory.getLogger(getClass());
            logger.error("Error parsing JSON response", e);
        }

        return Collections.emptyList(); // Return an empty list in case of failure
    }

    @Override
    public String getMarketChart(String coinId, int days, String apiKey) throws Exception {
        kong.unirest.HttpResponse<String> response = Unirest
                .get("https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=" + days)
                .header("accept", "application/json")
                .header("x-cg-demo-api-key", apiKey) //CG-NKcvLJG6zrc5LbPijBsFV9jL
                .queryString("days", days) // Add pagination if supported by the API
                .asString();

        // Check if the response status is 200 OK
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed to get data: " + response.getStatusText());
        }
        try {
            return response.getBody();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public String getCoinDetails(String coinId, String apiKey) throws Exception {
        kong.unirest.HttpResponse<String> response = Unirest.get("https://api.coingecko.com/api/v3/coins/" + coinId)
                .header("accept", "application/json")
                .header("x-cg-demo-api-key", apiKey) //CG-NKcvLJG6zrc5LbPijBsFV9jL
                .asString();

        // Check if the response status is 200 OK
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed to get data: " + response.getStatusText());
        }
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            Coin coin = new Coin();
            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());
            JsonNode marketData = jsonNode.get("market_data");
            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());
            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong());
            coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
            coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change_24h").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change_percentage_24h").asDouble());
            coin.setMarketCapChange24h(marketData.get("market_cap_change_24h").asLong());
            coin.setMarketCapChangePercentage24h(
                    marketData.get("market_cap_change_percentage_24h").asDouble());
            coin.setTotalSupply(marketData.get("total_supply").asDouble());

            coinRepository.save(coin);

            return response.getBody();
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    @Override
    public Coin findById(String coinId) throws Exception {
        java.util.Optional<Coin> coin = coinRepository.findById(coinId);
        if (coin.isEmpty())
            throw new Exception("Coin Not found");
        return coin.get();
    }

    @Override
    public String searchCoin(String keyWord, String apiKey) {
        kong.unirest.HttpResponse<String> response = Unirest
                .get("https://api.coingecko.com/api/v3/search?query=" + keyWord)
                .header("accept", "application/json")
                .header("x-cg-demo-api-key", apiKey) //CG-NKcvLJG6zrc5LbPijBsFV9jL
                .asString();

        // Check if the response status is 200 OK
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed to get data: " + response.getStatusText());
        }
        try {
            return response.getBody();
        } catch (Exception e) {
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return "";
    }

    @Override
    public String getTop50CoinsByMarketCapRank(String apiKey) {
        kong.unirest.HttpResponse<String> response = Unirest
                .get("https://api.coingecko.com/api/v3/coins/markets/?vs_currency=usd&per_page=50&page=1")
                .header("accept", "application/json")
                .header("x-cg-demo-api-key", apiKey) //CG-NKcvLJG6zrc5LbPijBsFV9jL
                .asString();

        // Check if the response status is 200 OK
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed to get data: " + response.getStatusText());
        }
        try {
            return response.getBody();
        } catch (Exception e) {
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return "";
    }

    @Override
    public String getTrendingCoins(String apiKey) {
        kong.unirest.HttpResponse<String> response = Unirest
                .get("https://api.coingecko.com/api/v3/search/trending")
                .header("accept", "application/json")
                .header("x-cg-demo-api-key", apiKey) //CG-NKcvLJG6zrc5LbPijBsFV9jL
                .asString();

        // Check if the response status is 200 OK
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed to get data: " + response.getStatusText());
        }
        try {
            return response.getBody();
        } catch (Exception e) {
            try {
                throw new Exception(e);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            };
        }

        return "";
    }

}

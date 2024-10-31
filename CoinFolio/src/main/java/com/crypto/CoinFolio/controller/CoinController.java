package com.crypto.CoinFolio.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.CoinFolio.model.Coin;
import com.crypto.CoinFolio.service.CoinService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.*;

@RestController
@RequestMapping("/coins")
public class CoinController {

    @Autowired
    private CoinService coinService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<Coin>> getCoinList(@RequestParam("page") int page, @RequestHeader("apiKey") String apiKey) {
        List<Coin> coins = coinService.getCoinList(page, apiKey);
        return ResponseEntity.ok(coins);
    }

    @GetMapping("/{coinId}/chart")
    public ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId, @RequestParam("days") int days,  @RequestHeader("apiKey") String apiKey) throws Exception {
        String coins = coinService.getMarketChart(coinId, days, apiKey);
        JsonNode jsonNode = objectMapper.readTree(coins);
        return ResponseEntity.ok(jsonNode);
    }

    @GetMapping("/{coinId}")
    public ResponseEntity<String> getCoinDetails(@PathVariable String coinId,  @RequestHeader("apiKey") String apiKey) throws Exception {
        String details = coinService.getCoinDetails(coinId, apiKey);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/find/{coinId}")
    public ResponseEntity<Coin> findById(@PathVariable String coinId) {
        try {
            Coin coin = coinService.findById(coinId);
            return ResponseEntity.ok(coin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<String> searchCoin(@RequestParam("keyword") String keyword,  @RequestHeader("apiKey") String apiKey) {
        String searchResult = coinService.searchCoin(keyword,apiKey);
        return ResponseEntity.ok(searchResult);
    }

    @GetMapping("/top-50")
    public ResponseEntity<String> getTop50CoinsByMarketCapRank(@RequestHeader("apiKey") String apiKey) {
        String top50Coins = coinService.getTop50CoinsByMarketCapRank(apiKey);
        return ResponseEntity.ok(top50Coins);
    }

    @GetMapping("/trending")
    public ResponseEntity<String> getTrendingCoins(@RequestHeader("apiKey") String apiKey) {
        String tradingCoins = coinService.getTrendingCoins(apiKey);
        return ResponseEntity.ok(tradingCoins);
    }


}

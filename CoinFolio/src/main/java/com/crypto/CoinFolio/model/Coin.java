package com.crypto.CoinFolio.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Coin {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image")
    private String image;

    @JsonProperty("current_price")
    private double currentPrice;

    @JsonProperty("market_cap")
    private double marketCap;

    @JsonProperty("market_cap_rank")
    private int marketCapRank;

    @JsonProperty("fully_diluted_valuation")
    private double fullyDilutedValuation;

    @JsonProperty("total_volume")
    private double totalVolume;

    @JsonProperty("high_24h")
    private double high24h;

    @JsonProperty("low_24h")
    private double low24h;

    @JsonProperty("price_change_24h")
    private double priceChange24h;

    @JsonProperty("price_change_percentage_24h")
    private double priceChangePercentage24h;

    @JsonProperty("market_cap_change_24h")
    private double marketCapChange24h;

    @JsonProperty("market_cap_change_percentage_24h")
    private double marketCapChangePercentage24h;

    @JsonProperty("circulating_supply")
    private double circulatingSupply;

    @JsonProperty("total_supply")
    private double totalSupply;

    @JsonProperty("max_supply")
    private double maxSupply;

    @JsonProperty("ath")
    private double ath; // All-Time High

    @JsonProperty("ath_change_percentage")
    private double athChangePercentage;

    @JsonProperty("ath_date")
    private String athDate; // Consider using LocalDateTime if you're working with dates

    @JsonProperty("atl")
    private double atl; // All-Time Low

    @JsonProperty("atl_change_percentage")
    private double atlChangePercentage;

    @JsonProperty("atl_date")
    private String atlDate; // Consider using LocalDateTime if you're working with dates

    @Transient
    @JsonProperty("roi")
    private Object roi; // Nullable; can be of a specific type based on your requirements

    @JsonProperty("last_updated")
    private String lastUpdated; // Consider using LocalDateTime if you're working with dates

}

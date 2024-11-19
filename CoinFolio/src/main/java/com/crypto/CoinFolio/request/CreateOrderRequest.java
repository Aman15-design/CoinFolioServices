package com.crypto.CoinFolio.request;

import com.crypto.CoinFolio.domain.OrderType;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private String coindId;
    private double quantity;
    private OrderType orderType;
}

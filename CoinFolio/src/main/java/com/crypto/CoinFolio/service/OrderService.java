package com.crypto.CoinFolio.service;

import java.util.List;

import com.crypto.CoinFolio.domain.OrderType;
import com.crypto.CoinFolio.model.Coin;
import com.crypto.CoinFolio.model.Order;
import com.crypto.CoinFolio.model.OrderItem;
import com.crypto.CoinFolio.model.User;

public interface OrderService {
    Order createOrder(User user,OrderItem orderItem,OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getAllOrdersOfUser(Long userId,String orderType,String assetSymbol);
    Order processOrder(Coin coin,double quantity,OrderType orderType,User user) throws Exception;
}

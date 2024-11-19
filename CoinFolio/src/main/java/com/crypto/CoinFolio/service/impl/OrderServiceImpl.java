package com.crypto.CoinFolio.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crypto.CoinFolio.domain.OrderStatus;
import com.crypto.CoinFolio.domain.OrderType;
import com.crypto.CoinFolio.model.Coin;
import com.crypto.CoinFolio.model.Order;
import com.crypto.CoinFolio.model.OrderItem;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.repository.OrderItemRepository;
import com.crypto.CoinFolio.repository.OrderRepository;
import com.crypto.CoinFolio.service.OrderService;
import com.crypto.CoinFolio.service.WalletService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();
        Order order = new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(price);
        order.setTimestamp(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found for id " + orderId));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType.equals(OrderType.BUY)){
            return buyAsset(coin, quantity, user);
        } else if(orderType.equals(OrderType.SELL)){
            return sellAsset(coin, quantity, user);
        } else {
            throw new Exception("Undefined Order Type");
        }
    }

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }

    @Transactional // here we need to give transactional annotation as in case any line of code in below method throws an exception, all the lines above that line would not execute and it would go to initial stage
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
        if (quantity <= 0) {
            throw new Exception("Quantity should be greater than 0");
        }
        double buyPrice = coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
        Order order = createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepository.save(order);

        // We would need to create asset here;

        return savedOrder;
    }

    @Transactional // here we need to give transactional annotation as in case any line of code in below method throws an exception, all the lines above that line would not execute and it would go to initial stage
    public Order sellAsset(Coin coin, double quantity, User user) throws Exception {
        if (quantity <= 0) {
            throw new Exception("Quantity should be greater than 0");
        }
        double sellPrice = coin.getCurrentPrice();
        // double buyPrice = assetToSell.getPrice();
        OrderItem orderItem = createOrderItem(coin, quantity, 0, sellPrice);
        Order order = createOrder(user, orderItem, OrderType.SELL);
        orderItem.setOrder(order);

        // if (assetToSell.getQuantity() >= quantity) {
        //     walletService.payOrderPayment(order, user);
        //     order.setOrderStatus(OrderStatus.SUCCESS);
        //     order.setOrderType(OrderType.SELL);
        //     Order savedOrder = orderRepository.save(order);

        //     Asset updatedAsset = assetService.updatedAsset(assetToSell, quantity);
        //     if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
        //         assetService.deleteAsset(updatedAsset.getId());
        //     }
        //     return savedOrder;
        // } else {
        //     throw new Exception();
        // }

        walletService.payOrderPayment(order, user);
        order.setOrderStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.SELL);
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }
}

package com.crypto.CoinFolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.CoinFolio.model.Coin;
import com.crypto.CoinFolio.model.Order;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.request.CreateOrderRequest;
import com.crypto.CoinFolio.service.CoinService;
import com.crypto.CoinFolio.service.OrderService;
import com.crypto.CoinFolio.service.UserService;


@RestController
@RequestMapping("/API/Orders")
public class OrderController {

    @Autowired
    UserService userService;
    
    @Autowired
    CoinService coinService;

    @Autowired
    OrderService orderService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(
        @RequestHeader("Authorization") String jwt,
        @RequestBody CreateOrderRequest request
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Coin coin = coinService.findById(request.getCoindId());

        Order order = orderService.processOrder(coin, request.getQuantity(), request.getOrderType(), user);

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> payOrderPayment(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long orderId
    ) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Order order = orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);   
        } else {
            throw new Exception("Invalid User");
        }
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrdersForUser(
        @RequestHeader("Authorization") String jwt,
        @RequestParam(required = false) String orderType,
        @RequestParam(required = false) String assetSymbol
    ) throws Exception{
        Long userId = userService.findUserByJwt(jwt).getId();

        List<Order> userOrders = orderService.getAllOrdersOfUser(userId, orderType, assetSymbol);
        return ResponseEntity.ok(userOrders);
    }
}

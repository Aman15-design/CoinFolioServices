package com.crypto.CoinFolio.model;

import com.crypto.CoinFolio.domain.OrderStatus;
import com.crypto.CoinFolio.domain.OrderType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders") 
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private double price;

    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;
}

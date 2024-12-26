package com.crypto.CoinFolio.model;

import org.hibernate.annotations.ManyToAny;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Asset {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    private double quantity;
    private double buyPrice;

    @ManyToAny
    private Coin coin;

    @ManyToOne
    private User user;
}

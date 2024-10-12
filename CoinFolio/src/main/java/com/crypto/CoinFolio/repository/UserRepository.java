package com.crypto.CoinFolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.CoinFolio.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
}

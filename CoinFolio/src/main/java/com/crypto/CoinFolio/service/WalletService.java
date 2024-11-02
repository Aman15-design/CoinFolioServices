package com.crypto.CoinFolio.service;

import com.crypto.CoinFolio.model.Order;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.model.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);

    Wallet addBalance(Wallet wallet, Double amount);

    Wallet findById(long id) throws Exception;

    Wallet walletToWalletTransfer(User sender, Wallet receiver, Double amount) throws Exception;

    Wallet payOrderPayment(Order order, User user);
}

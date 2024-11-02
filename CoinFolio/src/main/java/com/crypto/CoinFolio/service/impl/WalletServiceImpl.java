package com.crypto.CoinFolio.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crypto.CoinFolio.domain.OrderType;
import com.crypto.CoinFolio.model.Order;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.model.Wallet;
import com.crypto.CoinFolio.repository.WalletRepository;
import com.crypto.CoinFolio.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet = walletRepository.findByUserId(user.getId());
        if(wallet == null){
            wallet = new Wallet();
            wallet.setUser(user);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Double amount) {
        Double balance = wallet.getBalance();
        Double newBalance = balance + amount;
        wallet.setBalance(newBalance);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findById(long id) throws Exception {
        Optional<Wallet> wallet = walletRepository.findById(id);
        if(wallet.isPresent()){
            return wallet.get();
        }
        throw new Exception("Wallet not found");
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiver, Double amount) throws Exception {
        Wallet senderWallet = getUserWallet(sender);
        if(senderWallet != null){
            if(senderWallet.getBalance() <= amount){
                throw new Exception("Insufficient Balance");
            } else {
                Double newbalance = senderWallet.getBalance() - amount;
                senderWallet.setBalance(newbalance);

                Double receiverBalance = receiver.getBalance();
                receiverBalance = receiverBalance + amount;

                receiver.setBalance(receiverBalance);

                return senderWallet;
            }
        } else {
            throw new Exception("Wallet not found");
        }
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet = getUserWallet(user);

        if(order.getOrderType().equals(OrderType.BUY)){
            Double newBalance = wallet.getBalance() - order.getPrice();
            if(newBalance <= 0){
                throw new Exception("Insufficient Funds for transactions");
            } 
            wallet.setBalance(newBalance);
        } else {
            Double newBalance = wallet.getBalance() + order.getPrice();
            wallet.setBalance(newBalance);
        }
        walletRepository.save(wallet);
        return wallet;
    }
    
}

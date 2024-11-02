package com.crypto.CoinFolio.controller;

import com.crypto.CoinFolio.model.Order;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.model.Wallet;
import com.crypto.CoinFolio.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<Wallet> getUserWallet(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        Wallet wallet = walletService.getUserWallet(user);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/{walletId}/add-balance")
    public ResponseEntity<Wallet> addBalance(@PathVariable Long walletId, @RequestParam Double amount) throws Exception {
        Wallet wallet = walletService.findById(walletId);
        Wallet updatedWallet = walletService.addBalance(wallet, amount);
        return ResponseEntity.ok(updatedWallet);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam Double amount) throws Exception {
        User sender = new User();
        sender.setId(senderId);
        Wallet receiverWallet = walletService.findById(receiverId);
        
        Wallet updatedSenderWallet = walletService.walletToWalletTransfer(sender, receiverWallet, amount);
        walletService.addBalance(receiverWallet, amount);
        return ResponseEntity.ok(updatedSenderWallet);
    }

    @PostMapping("/{userId}/pay-order")
    public ResponseEntity<Wallet> payOrder(
            @PathVariable Long userId,
            @RequestBody Order order) throws Exception {
        User user = new User();
        user.setId(userId);
        Wallet wallet = walletService.payOrderPayment(order, user);
        return ResponseEntity.ok(wallet);
    }
}

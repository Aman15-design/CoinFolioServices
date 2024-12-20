package com.crypto.CoinFolio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "Welcome to CoinFolio";
    }

    @GetMapping("/api")
    public String homeSecure(){
        return "Welcome to CoinFolio Secured page";
    }
}

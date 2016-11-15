package com.controller;

import com.client.UserClient;
import com.model.Stock;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.YahooFinance;

import java.io.IOException;

@RestController
public class RestApiController {

    private final double startingBalance = 10000;

    @Autowired
    private UserClient userClient;

    @RequestMapping(method = RequestMethod.POST, value = "/createAccount")
    public User createAccount(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam String email) {
        User user = new User(name, address, password, email, startingBalance);
        userClient.createAccount(user);
        return user;
    }

    @RequestMapping("/getStock")
    public Stock getStock(@RequestParam String stockName) throws IOException {
        yahoofinance.Stock stock = YahooFinance.get(stockName);
        return new Stock(stockName, stock.getQuote().getPrice().doubleValue(), 1);
    }
}

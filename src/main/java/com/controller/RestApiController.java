package com.controller;

import com.client.StockClient;
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
import java.util.Map;

@RestController
public class RestApiController {

    private final double startingBalance = 10000;

    @Autowired
    private UserClient userClient;

    @Autowired
    private StockClient stockClient;

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

    @RequestMapping("/refreshStocks")
    /*
    Precondition: to-from <= 200
     */
    public Stock[] refreshStocks(@RequestParam int from, @RequestParam int to) throws IOException {
        return stockClient.refreshStocks(from, to);
    }
}

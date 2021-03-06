package com.controller;

import com.client.StockClient;
import com.client.UserClient;
import com.model.Stock;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    //user's starting $ balance
    @Value("${starting.balance}")
    private double startingBalance;

    //the user client object
    @Autowired
    private UserClient userClient;

    //the stock client object
    @Autowired
    private StockClient stockClient;

    /**
    * Creates a new user account with parameters passed from HTML input form
    */
    @RequestMapping(method = RequestMethod.POST, value = "/createAccount")
    public ResponseEntity<?> createAccount(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam String email) {
        User user = new User(name, address, password, email, startingBalance);
        userClient.createAccount(user);
        return ResponseEntity.ok(user);
    }

    /**
     * Returns the stock ticker data specific to the requesting user
     * Precondition: to-from <= 200
     */
    @RequestMapping(method = RequestMethod.GET, value = "/refreshStocks")
    public ResponseEntity<?> refreshStocks(
            @RequestParam int from,
            @RequestParam int to) {
        Stock[] stocks = stockClient.refreshStocks(from, to);
        if (stocks == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to retrieve Stock Data");
        } else {
            return ResponseEntity.ok(stocks);
        }
    }
    
    /**
     * Purchases stock for the user
     */
    @RequestMapping(method = RequestMethod.POST, value = "/purchaseStock")
    public ResponseEntity<?> purchaseStock(
            @RequestParam String stockName,
            @RequestParam String stockSymbol,
            @RequestParam int numberOfShares,
            @RequestParam String userName) {
        Double stockPrice = stockClient.getStockPrice(stockSymbol);
        if (stockPrice == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to retrieve Stock Data");
        }
        Stock stock = new Stock(stockName, stockSymbol, stockPrice, numberOfShares);
        User user = userClient.getUser(userName);
        if (stockClient.purchaseStock(user, stock) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inadequate Funds");
        } else {
            return ResponseEntity.ok(user);
        }
    }

    /**
     * User sells stock
     */
    @RequestMapping(method = RequestMethod.POST, value = "/sellStock")
    public ResponseEntity<?> sellStock(
            @RequestParam String stockName,
            @RequestParam String stockSymbol,
            @RequestParam int numberOfShares,
            @RequestParam String userName) {
        Double stockPrice = stockClient.getStockPrice(stockSymbol);
        if (stockPrice == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to retrieve Stock Data");
        }
        Stock stock = new Stock(stockName, stockSymbol, stockPrice, numberOfShares);
        User user = userClient.getUser(userName);
        if (stockClient.sellStock(user, stock) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock not Purchased or can not sell that many shares");
        } else {
            return ResponseEntity.ok(user);
        }
    }
}

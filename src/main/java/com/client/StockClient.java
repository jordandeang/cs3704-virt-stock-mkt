package com.client;

import com.controller.MongoController;
import com.model.Stock;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kevin on 11/15/2016.
 */
@Service
public class StockClient {

    @Autowired
    private MongoController mongoController;

    public User updateStock(User user, Stock stock) {
        if (user.getBalance() >= stock.getNumberOfShares()*stock.getStockPrice()) {
            double newBalance = user.getBalance() - stock.getNumberOfShares()*stock.getStockPrice();
            user.setBalance(newBalance);
            user.addStock(stock);
            mongoController.updateUser(user);
            return user;
        }
        else {
            return null;
        }
    }
}

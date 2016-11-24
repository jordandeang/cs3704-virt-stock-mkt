package com.client;

import com.controller.MongoController;
import com.model.Stock;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/15/2016.
 */
@Service
public class StockClient {

    @Autowired
    private MongoController mongoController;

    public User purchaseStock(User user, Stock stock) {
        if (user.getBalance() >= stock.getNumberOfShares()*stock.getStockPrice()) {
            double newBalance = user.getBalance() - stock.getNumberOfShares()*stock.getStockPrice();
            user.setBalance(newBalance);
            int index = findStock(stock.getStockName(), user.getStocklist());
            if (index != -1) {
                Stock updateStock = user.getStocklist().get(index);
                updateStock.setNumberOfShares(stock.getNumberOfShares() + updateStock.getNumberOfShares());
                updateStock.setStockPrice(stock.getStockPrice());
                user.removeStock(index);
                user.addStock(updateStock);
            }
            else {
                user.addStock(stock);
            }
            mongoController.updateUser(user);
            return user;
        }
        else {
            return null;
        }
    }

    public User sellStock(User user, Stock stock) {
        int index = findStock(stock.getStockName(), user.getStocklist());
        if (index != -1) {
            Stock updateStock = user.getStocklist().get(index);
            double updateShares = updateStock.getNumberOfShares() - stock.getNumberOfShares();
            if (updateShares < 0.0) {
                return null;
            }
            else {
                double newBalance = user.getBalance() + stock.getNumberOfShares()*stock.getStockPrice();
                user.setBalance(newBalance);
                if (updateShares == 0.0) {
                    user.removeStock(index);
                }
                else {
                    updateStock.setNumberOfShares(updateStock.getNumberOfShares() - stock.getNumberOfShares());
                    updateStock.setStockPrice(stock.getStockPrice());
                    user.removeStock(index);
                    user.addStock(updateStock);
                }
                mongoController.updateUser(user);
                return user;
            }
        }
        else {
            return null;
        }
    }


    private int findStock(String stockName, ArrayList<Stock> stockList) {
        for (Stock stock : stockList) {
            if (stock.getStockName().equals(stockName)) {
                return stockList.indexOf(stock);
            }
        }
        return -1;
    }
}

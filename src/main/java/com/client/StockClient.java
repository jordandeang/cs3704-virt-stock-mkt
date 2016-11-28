package com.client;

import com.controller.MongoController;
import com.model.Stock;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


@Service
public class StockClient {

    @Autowired
    private MongoController mongoController;

    @Value("${nasdaq.symbols}")
    private String[] NASDAQ_SYMBOLS;

    public Stock[] refreshStocks(int from, int to) {
        if (to >= NASDAQ_SYMBOLS.length) {
            to = NASDAQ_SYMBOLS.length;
        }
        Stock[] stocks = new Stock[to - from];
        int stocksIndex = 0;
        String[] currRange = Arrays.copyOfRange(NASDAQ_SYMBOLS, from, to);
        try {
            Map<String, yahoofinance.Stock> stocksRaw = YahooFinance.get(currRange);
            for (String symbol : currRange) {
                String stockName = stocksRaw.get(symbol).getName();
                stocks[stocksIndex] = new Stock(stockName, symbol, stocksRaw.get(symbol).getQuote().getPrice().doubleValue(), 1);
                stocksIndex++;
            }
            return stocks;
        } catch (IOException exception) {
            return null;
        }
    }

    public Double getStockPrice(String stockName) {
        try {
            return YahooFinance.get(stockName).getQuote().getPrice().doubleValue();
        } catch (IOException exception) {
            return null;
        }
    }

    public User purchaseStock(User user, Stock stock) {
        if (user.getBalance() >= stock.getNumberOfShares() * stock.getStockPrice()) {
            double newBalance = user.getBalance() - stock.getNumberOfShares() * stock.getStockPrice();
            user.setBalance(newBalance);
            int index = findStock(stock.getStockName(), user.getStocklist());
            if (index != -1) {
                Stock updateStock = user.getStocklist().get(index);
                updateStock.setNumberOfShares(stock.getNumberOfShares() + updateStock.getNumberOfShares());
                updateStock.setStockPrice(stock.getStockPrice());
                user.removeStock(index);
                user.addStock(updateStock);
            } else {
                user.addStock(stock);
            }
            mongoController.updateUser(user);
            return user;
        } else {
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
            } else {
                double newBalance = user.getBalance() + stock.getNumberOfShares() * stock.getStockPrice();
                user.setBalance(newBalance);
                if (updateShares == 0.0) {
                    user.removeStock(index);
                } else {
                    updateStock.setNumberOfShares(updateStock.getNumberOfShares() - stock.getNumberOfShares());
                    updateStock.setStockPrice(stock.getStockPrice());
                    user.removeStock(index);
                    user.addStock(updateStock);
                }
                mongoController.updateUser(user);
                return user;
            }
        } else {
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

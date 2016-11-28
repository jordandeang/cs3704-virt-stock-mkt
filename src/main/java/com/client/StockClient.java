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

/**
 * The StockClient class contains the functionality required to interact with stocks (buy, sell)
 *   and then update the respective User with their new stock information
 *
 * Note that a refreshStocks method is included with stock range parameters, since stock prices vary over time
 * A private method findStock is used when buying and selling to search a User's current stock information
 */
@Service
public class StockClient {

    // declare a private MongoController for database use
    @Autowired
    private MongoController mongoController;

    @Value("${nasdaq.symbols}")
    private String[] NASDAQ_SYMBOLS;

    /**
     * Refresh the stock list based on an index range to get most recent stock information
     * @param {int} from - Beginning of the range
     * @param {int} to - End of the range
     * @return Stock[]
     */
    public Stock[] refreshStocks(int from, int to) {
        // end of the range is the end of the NASDAQ stock list
        if (to >= NASDAQ_SYMBOLS.length) {
            to = NASDAQ_SYMBOLS.length;
        }

        // create the array of Stocks based on the range
        Stock[] stocks = new Stock[to - from];
        // reset index
        int stocksIndex = 0;
        // range value used for iteration
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

    /**
     * Get the current stock price from the YahooFinance API given a stock name
     * @param {String} stockName - The stock to find the price of
     * @return Double - the price of the stock
     */
    public Double getStockPrice(String stockName) {
        // retrieve a stock price from the YahooFinance API
        try {
            return YahooFinance.get(stockName).getQuote().getPrice().doubleValue();
        } catch (IOException exception) {
            return null;
        }
    }

    /**
     * Purchase a stock for a User given a certain Stock object (already contains the quantity)
     * @param {User} user - The User that is buying the stock
     * @param {Stock} stock - The Stock object that contains the information required to purchase the stock
     * @return User - the updated User object containing new user stock info based on the purchase
     */
    public User purchaseStock(User user, Stock stock) {

        // if the user has sufficient funds to purchase a stock
        if (user.getBalance() >= stock.getNumberOfShares() * stock.getStockPrice()) {
            // calculate the new balance and set it for the User
            double newBalance = user.getBalance() - stock.getNumberOfShares() * stock.getStockPrice();
            user.setBalance(newBalance);

            // get index of a stock in the User's stock list
            int index = findStock(stock.getStockName(), user.getStocklist());

            // found stock in User's stock list, update respective stock info
            if (index != -1) {
                // update the stock information, set the index
                Stock updateStock = user.getStocklist().get(index);
                updateStock.setNumberOfShares(stock.getNumberOfShares() + updateStock.getNumberOfShares());
                updateStock.setStockPrice(stock.getStockPrice());
                user.removeStock(index);
                user.addStock(updateStock);
            } else { // stock not already in User's stock list, add the stock to the stock list
                user.addStock(stock);
            }
            // make sure to update the User's mongodb information
            mongoController.updateUser(user);
            return user;
        } else { // User does not have sufficient funds to buy the stock
            return null;
        }
    }

    /**
     * Sell a stock for a User given a certain Stock object (already contains the quantity)
     * @param {User} user - The User that is selling the stock
     * @param {Stock} stock - The Stock object that contains the information required to sell the stock
     * @return User - the updated User object containing new user stock info based on the sell
     */
    public User sellStock(User user, Stock stock) {
        // see if a User has the stock first
        int index = findStock(stock.getStockName(), user.getStocklist());

        // User has stock in stock list...
        if (index != -1) {
            // now update the user's stock information based on sell request
            Stock updateStock = user.getStocklist().get(index);
            double updateShares = updateStock.getNumberOfShares() - stock.getNumberOfShares();
            // check that updateShares is a valid value
            if (updateShares < 0.0) { // not valid, return null
                return null;
            } else { // valid updateShares request
                // User has a new balance value
                double newBalance = user.getBalance() + stock.getNumberOfShares() * stock.getStockPrice();
                user.setBalance(newBalance);

                // Remove stock from list if no more shares left
                if (updateShares == 0.0) {
                    user.removeStock(index);
                }
                // Update the stock info in the stock list if still shares left
                else {
                    updateStock.setNumberOfShares(updateStock.getNumberOfShares() - stock.getNumberOfShares());
                    updateStock.setStockPrice(stock.getStockPrice());
                    user.removeStock(index);
                    user.addStock(updateStock);
                }
                mongoController.updateUser(user); // make sure to update the database information from MongoDB
                return user;
            }
        } else { // case where user does not have the stock wanted to sell in stock list
            return null;
        }
    }

    /**
     * Attempt to find a certain stock in the User's ArrayList of Stock objects
     * @param {String} stockName - The stock to search for
     * @param {ArrayList<Stock>} stockList - The User's list of Stock objects to search through
     * @return int - the index of the stock
     */
    private int findStock(String stockName, ArrayList<Stock> stockList) {
        for (Stock stock : stockList) {
            // if a matched stock name is found
            if (stock.getStockName().equals(stockName)) {
                return stockList.indexOf(stock);
            }
        }
        // return -1 if nothing found
        return -1;
    }
}

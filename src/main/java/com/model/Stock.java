package com.model;

/**
 * Created by Kevin on 11/15/2016.
 */

/**
 * The Stock class represents a Stock object with name, price, shares, symbol, used to interact with a User so that
 * that User can obtain stock information and buy/sell.
 *
 * Contains methods that get a certain stock's name and symbol, as well as get and set a stock price.
 *  Methods included to set and get the number of shares of this Stock object.
 * The constructor is used to instantiate a Stock object with name, price, shares, symbol info.
 */
public class Stock {

    // private declarations
    private final String stockName;
    private double stockPrice;
    private int numberOfShares;
    private final String stockSymbol;

    /**
     * Construct a Stock object
     * @param {String} stockName - the stock we are creating a Stock object for
     * @param {String} stockSymbol - stock symbol for the respective stock
     * @param {double} stockPrice - the price of the stock
     * @param {int} numberOfShares - the number of shares respective to the stock represented by this Stock object
     */
    public Stock(String stockName, String stockSymbol, double stockPrice, int numberOfShares) {
        this.stockName = stockName;
        this.stockSymbol = stockSymbol;
        this.stockPrice = stockPrice;
        this.numberOfShares = numberOfShares;
    }

    /**
     * Get the name of the stock contained in a Stock object
     * @return String - the name of the stock
     */
    public String getStockName() {
        return stockName;
    }

    /**
     * Get the symbol of the stock contained in a Stock object
     * @return String - the symbol of the stock
     */
    public String getStockSymbol() {
        return stockSymbol;
    }

    /**
     * Get the price of the stock contained in a Stock object
     * @return double - the price of the stock
     */
    public double getStockPrice() {
        return stockPrice;
    }

    /**
     * Set a price for the stock contained in a Stock object
     * @param {double} stockPrice - The new price to be set
     */
    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    /**
     * Get the number of shares of the stock contained in a Stock object
     * @return int - the number of shares respective to the stock in the Stock object
     */
    public int getNumberOfShares() {
        return numberOfShares;
    }

    /**
     * Set the number of shares for the stock contained in a Stock object
     * @param {int} numberOfShares - The new number of shares to be set
     */
    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }
}

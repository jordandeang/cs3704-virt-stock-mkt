package com.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pjw on 11/27/16.
 */
public class StockTest {

    String stockName = "TestStock";
    String stockSymbol = "ABC";
    double stockPrice = 2.0;
    int numberOfShares = 5;
    Stock stock = new Stock(stockName, stockSymbol, stockPrice, numberOfShares);

    // test that we can get a stock name correctly
    @Test
    public void getStockName() {
        String testName = "TestStock";
        String name = stock.getStockName();
        assertTrue(testName == name);
    }

    // test that we can get a stock price correctly
    @Test
    public void getStockPrice() {
        double testPrice = 2.0;
        double stockPrice = stock.getStockPrice();
        assertTrue(testPrice == stockPrice);
    }

    // make sure we can correctly set a stock price
    @Test
    public void setStockPrice() {
        double testPrice = 5.0;
        stock.setStockPrice(5.0);
        assertTrue(testPrice == stock.getStockPrice());
    }

    // test getting a number of shares selected for a stock
    @Test
    public void getNumberOfShares() {
        int testShares = 5;
        int numberOfShares = stock.getNumberOfShares();
        assertTrue(testShares == numberOfShares);
    }

    // test setting a number of shares for a stock
    @Test
    public void setNumberOfShares() {
        int testShares = 10; // new value of shares
        stock.setNumberOfShares(10);
        assertTrue(testShares == stock.getNumberOfShares());
    }

}
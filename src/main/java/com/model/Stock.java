package com.model;

/**
 * Created by Kevin on 11/15/2016.
 */
public class Stock {
    private final String stockName;
    private double stockPrice;
    private final int numberOfShares;

    public Stock(String stockName, double stockPrice, int numberOfShares) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.numberOfShares = numberOfShares;
    }

    public String getStockName() {
        return stockName;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockName='" + stockName + '\'' +
                ", stockPrice=" + stockPrice +
                ", numberOfShares=" + numberOfShares +
                '}';
    }
}

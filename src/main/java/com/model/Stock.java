package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Jordan on 11/15/2016.
 */
public class Stock {

    private final String stockName;
    private final double stockPrice;
    private final int quantity;

    public Stock(String stockName, double stockPrice, int quantity) {
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.quantity = quantity;
    }

    public String getStockName() {
        return stockName;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "Stock{" +
                "stockName='" + stockName + '\'' +
                ", stockPrice='" + stockPrice + '\'' +
                ", quantity=" + quantity+
                '}';
    }
}

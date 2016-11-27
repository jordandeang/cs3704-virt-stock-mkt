package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/14/2016.
 */
@Document(collection="users")
public class User {

    @Id
    private String id;

    private final String name, address, password, email;
    //TODO - Add list of stocks and quantity of each
    private ArrayList<Stock> stockList;
    private double balance;


    public User(String name, String address, String password, String email, double balance) {
        this.name = name;
        this.address = address;
        this.password = password;
        this.email = email;
        this.balance = balance;
        stockList = new ArrayList<Stock>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addStock(Stock stock) {
        stockList.add(stock);
    }

    public ArrayList<Stock> getStocklist() {
        return stockList;
    }

    public void removeStock(int index) {
        stockList.remove(index);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
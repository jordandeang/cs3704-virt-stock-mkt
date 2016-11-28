package com.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Kevin on 11/14/2016.
 */
@Document(collection="users")
public class User extends org.springframework.security.core.userdetails.User{

    @Id
    private String id;

    private final String name, address, email;
    private ArrayList<Stock> stockList;
    private double balance;


    public User(String name, String address, String password, String email, double balance) {
        super(name, password, Arrays.asList(new SimpleGrantedAuthority("USER")));
        this.name = name;
        this.address = address;
        this.email = email;
        this.balance = balance;
        stockList = new ArrayList<Stock>();
    }

    public String getAddress() {
        return address;
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

}
package com.model;
import com.model.Stock;
import com.model.User;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Created by pjw on 11/27/16.
 */
public class UserTest {

    String name = "Joe Bob";
    String address = "Blacksburg, VA";
    String password = "password";
    String email = "joe.bob@email.com";
    double balance = 100;
    User user = new User(name, address, password, email, balance);

    String stockName = "TestStock";
    String stockSymbol = "ABC";
    double stockPrice = 2.0;
    int numberOfShares = 5;
    Stock stock = new Stock(stockName, stockSymbol, stockPrice, numberOfShares);

    ArrayList<Stock> list;

    // check to see if a simple addition of a stock to an arraylist works correctly
    @Test
    public void checkAddStock() {
        list = new ArrayList<Stock>();
        list.add(stock);

        user.addStock(stock);
        assertEquals(user.getStocklist(), list);
    }

    // check to see if adding a stock and then removing stock will work correctly
    @Test
    public void checkRemoveStock() {
        list = new ArrayList<Stock>();
        user.addStock(stock);

        user.removeStock(0);
        assertEquals(user.getStocklist(), list);
    }

    // make sure we can get the balance of a user correctly
    @Test
    public void checkGetBalance() {
        double testBal = 100; // note that user balance is also defined as 100 at head of test class
        double userBal = user.getBalance();

        assertTrue(testBal == userBal);
    }

    // make sure we can set the balance correctly
    @Test
    public void checkSetBalance() {
        double testBal = 200; // new balance of 200
        user.setBalance(testBal);

        assertTrue(user.getBalance() == 200);
    }

    // check to make sure a name can be retrieved correctly
    @Test
    public void checkGetName() {
        String testName = "Joe Bob";
        String userName = user.getName();

        assertTrue(testName == userName);
    }

    // check to make sure an email can be retrieved correctly
    @Test
    public void checkGetEmail() {
        String testName = "joe.bob@email.com";
        String userName = user.getEmail();

        assertTrue(testName == userName);
    }

    // check to make sure a password can be retrieved correctly
    @Test
    public void checkGetPassword() {
        String testName = "password";
        String userName = user.getPassword();

        assertTrue(testName == userName);
    }


}
package com.client;

import com.controller.MongoController;
import com.model.Stock;
import com.model.User;
import com.repository.userRepository;
import com.client.StockClient;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by pjw on 11/27/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class StockClientTest {


    @Mock
    private userRepository userRepository;

    @Mock
    private MongoController mongoController;

    StockClient sc1 = new StockClient();

    @Mock
    StockClient sc2 = mock(StockClient.class);

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

    @Mock
    Stock testStock = mock(Stock.class);

    @Test
    public void checkGetStockPrice() {
        String stockName = "ABC";
        assertNotNull(sc1.getStockPrice(stockName));
    }


    @Test
    public void checkRefreshStocks() {
        assertNull(sc2.refreshStocks(1, 0));
    }

    @Test
    public void checkPurchaseStock() {

        //when(testStock.getStockPrice()).thenReturn(5.0);
        //when(testStock.getNumberOfShares()).thenReturn(2);

        assertNull(sc2.purchaseStock(user, testStock));
    }

    // check null case for selling stock
    @Test
    public void checkSellStock() {
        assertNull(sc2.sellStock(user, stock));
    }
}




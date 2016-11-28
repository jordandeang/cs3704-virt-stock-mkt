package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Stock;
import com.model.User;
import com.repository.userRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kevin on 11/27/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestApiControllerTest {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private userRepository userRepository;

    @Test
    public void createAccount() throws Exception {
        String baseURL = "http://localhost:8080/createAccount";
        //Building the Request body data
        Map<String, String> requestBody = new HashMap<String, String>();
        requestBody.put("name", "Kevin");
        requestBody.put("address", "QWER1234");
        requestBody.put("password", "Kevin");
        requestBody.put("email", "Kasdasd");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
        ResponseEntity<Map> response =
                restTemplate.postForEntity(baseURL + "?name=" +
                        requestBody.get("name") + "&address="
                        + requestBody.get("address")
                        + "&password=" + requestBody.get("password")
                        + "&email=" + requestBody.get("email"), httpEntity, Map.class, Collections.EMPTY_MAP);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Map createdUser = response.getBody();
        User user = userRepository.findByName(createdUser.get("name").toString());
        assertEquals(user.getName(), "Kevin");
        assertEquals(user.getAddress(), "QWER1234");
        assertEquals(user.getPassword(), "Kevin");
        assertEquals(user.getEmail(), "Kasdasd");
        assertEquals(user.getBalance(), 10000, 1);
        userRepository.deleteAll();
    }

    @Test
    public void purchaseStock() throws Exception {
        String baseURL = "http://localhost:8080/purchaseStock";
        //Save initial user
        User user = new User("Kevin", "asdasd", "password", "kkoehncke@hotmail.com", 10000);
        userRepository.save(user);
        //Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("stockName", "Apple");
        requestBody.put("stockSymbol", "AAPL");
        requestBody.put("numberOfShares", 10);
        requestBody.put("userName", "Kevin");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
        ResponseEntity<Map> response =
                restTemplate.postForEntity(baseURL + "?stockName=" +
                        requestBody.get("stockName") + "&stockSymbol="
                        + requestBody.get("stockSymbol")
                        + "&numberOfShares=" + requestBody.get("numberOfShares")
                        + "&userName=" + requestBody.get("userName"), httpEntity, Map.class, Collections.EMPTY_MAP);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Map createdUser = response.getBody();
        User userUpdated = userRepository.findByName(createdUser.get("name").toString());
        assertEquals(userUpdated.getBalance(), user.getBalance() - userUpdated.getStocklist().get(0).getStockPrice() * userUpdated.getStocklist().get(0).getNumberOfShares(), 1);
        assertEquals(userUpdated.getStocklist().get(0).getStockName(), "Apple");
        assertEquals(userUpdated.getStocklist().get(0).getStockSymbol(), "AAPL");
        assertEquals(userUpdated.getStocklist().get(0).getNumberOfShares(), 10);
        userRepository.deleteAll();
    }

    @Test
    public void sellStock() throws Exception {
        String baseURL = "http://localhost:8080/sellStock";
        //Save initial user with initial stock
        User user = new User("Kevin", "asdasd", "password", "kkoehncke@hotmail.com", 10000);
        Stock stock = new Stock("Apple", "AAPL", 112, 10);
        user.addStock(stock);
        userRepository.save(user);
        //Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("stockName", "Apple");
        requestBody.put("stockSymbol", "AAPL");
        requestBody.put("numberOfShares", 9);
        requestBody.put("userName", "Kevin");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(requestBody), requestHeaders);
        ResponseEntity<Map> response =
                restTemplate.postForEntity(baseURL + "?stockName=" +
                        requestBody.get("stockName") + "&stockSymbol="
                        + requestBody.get("stockSymbol")
                        + "&numberOfShares=" + requestBody.get("numberOfShares")
                        + "&userName=" + requestBody.get("userName"), httpEntity, Map.class, Collections.EMPTY_MAP);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Map createdUser = response.getBody();
        User userUpdated = userRepository.findByName(createdUser.get("name").toString());
        int numberOfSharesSold = (int) requestBody.get("numberOfShares");
        assertEquals(userUpdated.getBalance(), user.getBalance() + userUpdated.getStocklist().get(0).getStockPrice()*numberOfSharesSold, 1);
        assertEquals(userUpdated.getStocklist().get(0).getStockName(), "Apple");
        assertEquals(userUpdated.getStocklist().get(0).getStockSymbol(), "AAPL");
        assertEquals(userUpdated.getStocklist().get(0).getNumberOfShares(), 1);
        userRepository.deleteAll();
    }
}


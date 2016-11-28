package com.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Kevin on 11/27/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestApiControllerTest {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private RestTemplate restTemplate = new RestTemplate();

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
        //Invoking the API
        Map<String, Object> apiResponse =
                restTemplate.postForObject(baseURL + "?name=" +
                        requestBody.get("name") + "&address="
                        + requestBody.get("address")
                        + "&password=" + requestBody.get("password")
                        + "&email=" + requestBody.get("email"), httpEntity, Map.class, Collections.EMPTY_MAP);
        assertNotNull(apiResponse);
    }

    @Test
    public void purchaseStock() throws Exception {

    }

    @Test
    public void sellStock() throws Exception {

    }

}
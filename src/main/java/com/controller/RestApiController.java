package com.controller;

import com.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    private final double startingBalance = 10000;

    @RequestMapping(method = RequestMethod.POST, value = "/createAccount")
    public User createAccount(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String password,
            @RequestParam String email) {
        User user = new User(name, address, password, email, startingBalance);
        return user;
    }
}

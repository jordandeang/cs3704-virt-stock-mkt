package com.controller;

import com.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    private static final String template = "Hello, %s!";

    @RequestMapping("/greeting")
    public User greeting(@RequestParam(value="name", defaultValue="World") String name) {
        System.out.println("hello!");
        return new User(1,
                String.format(template, name));
    }
}

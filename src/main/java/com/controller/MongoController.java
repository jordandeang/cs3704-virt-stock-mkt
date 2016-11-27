package com.controller;

import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.repository.userRepository;
import org.springframework.stereotype.Controller;

/**
 * Created by Kevin on 11/14/2016.
 */
@Controller
public class MongoController {

    @Autowired
    private userRepository userRepository;

    public void createAccount(User user) {
        userRepository.save(user);
    }

    public User getUser(String user) {
        return userRepository.findByName(user);
    }

    public User updateUser(User user) {
        userRepository.save(user);
        return user;
    }
}

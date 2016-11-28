package com.client;

import com.controller.MongoController;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kevin on 11/14/2016.
 */
@Service
public class UserClient{

    @Autowired
    private MongoController mongoController;

    public void createAccount(User user) {
        mongoController.createAccount(user);
    }

    public User getUser(String user) {
        return mongoController.getUser(user);
    }

}

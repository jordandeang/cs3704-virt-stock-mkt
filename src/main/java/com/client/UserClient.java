package com.client;

import com.controller.MongoController;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Kevin on 11/14/2016.
 */

/**
 * The UserClient class contains the functionality required for a user to create an account within
 *  a MongoController, as well as a method to retrieve a User from the mongo database
 */
@Service
public class UserClient{

    @Autowired
    private MongoController mongoController; // private MongoController used to access MongoDB info

    /**
     * Create an account in MongoDB given a User object containing that user's info
     * @param {User} user - The User we wish to add to the database
     */
    public void createAccount(User user) {
        mongoController.createAccount(user);
    }

    /**
     * Retrieve a user by the User's name
     * @param {String} user - The user's name we wish to retrieve the User object for
     * @return User - the User found in the database based on the name given as a parameter
     */
    public User getUser(String user) {
        return mongoController.getUser(user);
    }

}

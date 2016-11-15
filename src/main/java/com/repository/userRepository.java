package com.repository;

import com.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Kevin on 11/14/2016.
 */
public interface userRepository extends MongoRepository<User, String> {
    public User findByName(String name);
}

package com.controller;

import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.repository.userRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Kevin on 11/14/2016.
 */
@Controller
public class MongoController  implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }else{
            List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}

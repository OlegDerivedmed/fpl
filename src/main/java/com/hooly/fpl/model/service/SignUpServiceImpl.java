package com.hooly.fpl.model.service;

import com.hooly.fpl.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpServiceImpl {

    @Autowired
    private UserServiceImpl userService;

    public User createUser(String login, String password, String firstName, String lastName, String isLearner) {
        return userService.save(login, password, firstName, lastName, isLearner);
    }
}

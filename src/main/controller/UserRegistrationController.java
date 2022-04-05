package main.controller;

import main.Data.Gender;
import main.Data.User;
import main.service.UserService;

public class UserRegistrationController {
    private  final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    public User createUser(String name, Gender gender, int age) {
        return userService.addUser(name,gender,age);
    }

}

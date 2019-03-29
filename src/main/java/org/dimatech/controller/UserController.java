package org.dimatech.controller;

import org.dimatech.model.User;
import org.dimatech.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private CustomUserDetailsService userService;
    @GetMapping(value = "/api/users")
    public List<User> getAllUsers(){
        List <User> tmp = userService.findAllUsers();
        return tmp;
    }

    @PostMapping(value = "/api/users/update/state/{id}")
    public String updateUser(@PathVariable String id) {

        userService.changeStateUser(id);
        return "OK!";
    }

    @DeleteMapping(value = "/api/users/delete/{id}")
    public String deleteUser(@PathVariable String id) {

        userService.deleteUser(userService.findUserById(id));
        return "OK!";
    }




}

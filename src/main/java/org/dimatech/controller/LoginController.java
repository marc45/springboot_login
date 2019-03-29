package org.dimatech.controller;
import org.dimatech.model.Role;
import org.dimatech.model.User;
import org.dimatech.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;


@Controller
public class LoginController {

    @Autowired
    private CustomUserDetailsService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }


    @RequestMapping("/save")
    public String save(@RequestParam String userid, @RequestParam String usermail, @RequestParam String userpass, @RequestParam String userfullname, @RequestParam Boolean userenabled, @RequestParam Set<Role> userroles) {
        User user = new User();
        user.setId(userid);
        user.setEmail(usermail);
        user.setPassword(userpass);
        user.setFullname(userfullname);
        user.setEnabled(userenabled);
        user.setRoles(userroles);
        userService.saveUser(user);

        return "redirect:/show/" + user.getId();
    }

}
package org.dimatech.controller;


import org.dimatech.model.Role;
import org.dimatech.model.User;
import org.dimatech.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class WebController {


    @Autowired
    private CustomUserDetailsService userService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/user")
    public String userDashboard(Model model) {
        //model.addAttribute("users", userService.findAllUsers());
        return "user";
    }



    @RequestMapping("/profile")
    public String userProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("currentUser", user);
        return "profile";

    }



    @RequestMapping("/create")
    public String create(Model model) {
        return "create";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }


    @RequestMapping("/show/{id}")
    public String show(@PathVariable String Id, Model model) {
        model.addAttribute("user", userService.findUserById(Id));
        return "show";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam String Id) {
        User user = userService.findUserById(Id);
        userService.deleteUser(user);

        return "redirect:/user";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String Id, Model model) {
        model.addAttribute("user", userService.findUserById(Id));
        return "edit";
    }

    @RequestMapping("/update")
    public String update(@RequestParam String userid, @RequestParam String usermail, @RequestParam String userpass, @RequestParam String userfullname,@RequestParam Boolean userenabled, @RequestParam Set<Role> userroles) {
        User user = userService.findUserById(userid);
        user.setId(userid);
        user.setEmail(usermail);
        user.setPassword(userpass);
        user.setFullname(userfullname);
        user.setEnabled(userenabled);
        user.setRoles(userroles);
        userService.saveUser(user);

        return "redirect:/show/" + user.getId();
    }


    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

}

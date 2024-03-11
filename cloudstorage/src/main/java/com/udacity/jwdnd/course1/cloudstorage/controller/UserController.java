package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    //sign up
    @PostMapping("/register")
    public String signUp(User user, RedirectAttributes redirectAttributes,Model model) {
        if (user == null) {
            return "redirect:signup";
        }
        boolean isUserExist = userService.isExistUser(user);
        try {
            if (isUserExist == true) {
                userService.registerUser(user);
                redirectAttributes.addFlashAttribute("message","Registered Successfully, Please login");
                return "redirect:/login?success";
            } else {
                model.addAttribute("mess","Register fail, Username already existed");
                return "/signup";
            }


        } catch (Exception e) {
            model.addAttribute("mess","Register fail, Username already existed");
            return "/signup";
        }


    }


    @GetMapping("/signup")
    public String signPage() {
        return "/signup";
    }
}

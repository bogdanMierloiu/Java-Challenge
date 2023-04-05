package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.security.AppUser;
import com.bogdanmierloiu.Java_Challenge.security.LoginProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class IndexController {

    @GetMapping
    public String goToIndex(Authentication authentication, Model model) {
        Object principal = authentication.getPrincipal();
        if(principal instanceof AppUser){
            AppUser appUser = (AppUser) principal;
            LoginProvider provider = appUser.getProvider();
            if( provider == LoginProvider.APP){

            } else if(provider == LoginProvider.GOOGLE){

            } else if (provider == LoginProvider.GITHUB) {

            }
            String name = null;
            String email = null;
            if (provider == LoginProvider.APP) {
                name = appUser.getUsername();
                email = appUser.getEmail();
            } else if (provider == LoginProvider.GOOGLE) {
                name = appUser.getAttribute("name");
                email = appUser.getAttribute("email");
            } else if (provider == LoginProvider.GITHUB) {
                name = appUser.getAttribute("login");
                email = appUser.getAttribute("email");
            }
            record User(String name, String email) {
            }
            User user = new User(name, email);
            model.addAttribute("user", user);
        } else {

        }
//        String name = ((AppUser) authentication.getPrincipal()).getAttribute("name");
//        String email = ((AppUser) authentication.getPrincipal()).getAttribute("email");



        return "index";
    }
}

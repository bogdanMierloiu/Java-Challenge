package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
        String name = ((OAuth2User) authentication.getPrincipal()).getAttribute("name");
        String email = ((OAuth2User) authentication.getPrincipal()).getAttribute("email");
        record User(String name, String email) {
        }
        User user = new User(name, email);
        model.addAttribute("user", user);
        return "index";
    }
}

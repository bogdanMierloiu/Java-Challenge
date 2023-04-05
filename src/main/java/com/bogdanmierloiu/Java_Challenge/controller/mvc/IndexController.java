package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.security.AppUser;
import com.bogdanmierloiu.Java_Challenge.security.LoginProvider;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;

@Controller
@RequestMapping
@SessionAttributes("player")
@RequiredArgsConstructor
public class IndexController {

    private final PlayerService playerService;

    @GetMapping
    public String goToIndex(Authentication authentication, Model model) {
        String name = null;
        try {
            name = ((AppUser) authentication.getPrincipal()).getAttribute("name");
        } catch (Exception e) {
            name = ((AppUser) authentication.getPrincipal()).getUsername();
        }
        PlayerResponse playerResponse = playerService.findByName(name);
        if (playerResponse == null) {
            PlayerRequest player = new PlayerRequest();
            player.setName(name);
            playerResponse = playerService.add(player);
        }
        model.addAttribute("player", playerResponse);
        return "index";
    }


}

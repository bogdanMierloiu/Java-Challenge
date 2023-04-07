package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.security.AppUser;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import com.bogdanmierloiu.Java_Challenge.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/admin")
@SessionAttributes("player")
@RequiredArgsConstructor
public class AdminController {
    private final PlayerService playerService;
    private final HttpSession session;

    public static void checkUser(Authentication authentication, Model model, PlayerService playerService) {
        String name;
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
    }

    @GetMapping("/admin-page")
    public String goToAdminPage(Authentication authentication, Model model) {
        checkUser(authentication, model, playerService);
        return "admin-page";
    }

    private boolean isAdmin() {
        PlayerResponse playerFromSession = (PlayerResponse) session.getAttribute("player");
        return Objects.equals(playerFromSession.getName(), "admin");
    }


    @GetMapping("/all-players")
    public String showAllPlayers(Model model) {
        if (!isAdmin()) {
            return "access-denied";
        }
        model.addAttribute("players", playerService.getAll());
        return "admin-all-players";

    }


}

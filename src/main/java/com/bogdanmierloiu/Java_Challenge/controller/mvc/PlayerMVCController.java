package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("player")
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerMVCController {

    private final PlayerService playerService;

    //    @GetMapping("/account/{id}")
//    public String goToAccount(@PathVariable Long id, Model model) {
//        model.addAttribute("player", playerService.findById(id));
//        return "player-account";
//    }
    @GetMapping("/account")
    public String goToAccount(HttpSession session, Model model) {
        model.addAttribute("player", (PlayerResponse) session.getAttribute("player"));
        return "player-account";
    }

}

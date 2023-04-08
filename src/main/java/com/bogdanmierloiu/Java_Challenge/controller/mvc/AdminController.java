package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.security.AppUser;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import com.bogdanmierloiu.Java_Challenge.service.QuestionService;
import com.bogdanmierloiu.Java_Challenge.service.WalletHistoryService;
import com.bogdanmierloiu.Java_Challenge.service.WalletService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Objects;

@Controller
@RequestMapping("/admin")
@SessionAttributes("player")
@RequiredArgsConstructor
public class AdminController {
    private final PlayerService playerService;
    private final QuestionService questionService;
    private final WalletService walletService;
    private final WalletHistoryService walletHistoryService;
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
        model.addAttribute("players", playerService.findAllByOrderByTokens());
        return "admin-all-players";

    }

    @GetMapping("/all-questions")
    public String showAllQuestions(Model model) {
        if (!isAdmin()) {
            return "access-denied";
        }
        model.addAttribute("questions", questionService.getAll());
        return "admin-all-questions";
    }

    @GetMapping("/all-wallets")
    public String showAllWallets(Model model) {
        if (!isAdmin()) {
            return "access-denied";
        }
        model.addAttribute("wallets", walletService.getAll());
        return "admin-all-wallets";
    }

    @GetMapping("/wallet-history/{walletId}")
    public String showWalletHistory(@PathVariable("walletId") String walletId, Model model) {
        if (!isAdmin()) {
            return "access-denied";
        }
        model.addAttribute("player", playerService.findByWalletId(Long.parseLong(walletId)));
        model.addAttribute("walletHistory", walletHistoryService.findByWallet(Long.parseLong(walletId)));
        return "admin-wallet-history";
    }


}

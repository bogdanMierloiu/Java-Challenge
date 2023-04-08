package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.exception.AccessDeniedException;
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
import org.springframework.web.bind.annotation.*;

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

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e) {
        return "access-denied";
    }

    private void isAdmin() throws AccessDeniedException {
        PlayerResponse playerFromSession = (PlayerResponse) session.getAttribute("player");
        if (playerFromSession == null || !Objects.equals(playerFromSession.getName(), "admin")) {
            throw new AccessDeniedException("Access denied");
        }
    }

    @GetMapping("/admin-page")
    public String goToAdminPage() throws AccessDeniedException {
        isAdmin();
        return "admin-page";
    }

    @GetMapping("/all-players")
    public String showAllPlayers(Model model) throws AccessDeniedException {
        isAdmin();
        model.addAttribute("players", playerService.findAllByOrderByTokens());
        return "admin-all-players";
    }

    @GetMapping("/all-questions")
    public String showAllQuestions(Model model) throws AccessDeniedException {
        isAdmin();
        model.addAttribute("questions", questionService.getAll());
        return "admin-all-questions";
    }

    @GetMapping("/all-wallets")
    public String showAllWallets(Model model) throws AccessDeniedException {
        isAdmin();
        model.addAttribute("wallets", walletService.getAll());
        return "admin-all-wallets";
    }

    @GetMapping("/wallet-history/{walletId}")
    public String showWalletHistory(@PathVariable("walletId") String walletId, Model model) throws AccessDeniedException {
        isAdmin();
        model.addAttribute("player", playerService.findByWalletId(Long.parseLong(walletId)));
        model.addAttribute("walletHistory", walletHistoryService.findByWallet(Long.parseLong(walletId)));
        return "admin-wallet-history";
    }


}

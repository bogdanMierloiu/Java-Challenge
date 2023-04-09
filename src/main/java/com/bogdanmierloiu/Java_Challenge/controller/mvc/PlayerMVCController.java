package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import com.bogdanmierloiu.Java_Challenge.dto.wallet_history.WalletHistoryResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Nft;
import com.bogdanmierloiu.Java_Challenge.service.NftService;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import com.bogdanmierloiu.Java_Challenge.service.WalletHistoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@Controller
@SessionAttributes("player")
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerMVCController {

    private final WalletHistoryService walletHistoryService;
    private final NftService nftService;

    private final PlayerService playerService;

    @GetMapping("/account")
    public String goToAccount(HttpSession session, Model model) {
        PlayerResponse player = (PlayerResponse) session.getAttribute("player");
        WalletResponse wallet = player.getWallet();
        List<WalletHistoryResponse> walletHistory = walletHistoryService.findByWallet(player.getId());
        List<Nft> nftList = nftService.findByWallet(player.getWallet().getId());
        model.addAttribute("player", player);
        model.addAttribute("wallet", wallet);
        model.addAttribute("walletHistory", walletHistory);
        model.addAttribute("nftList", nftList);
        return "player-account";
    }

    @GetMapping("/find")
    public String findByWalletAddress(@RequestParam("walletAddress") String walletAddress, Model model) {
        try {
            model.addAttribute("playerFound", playerService.findByWalletAddress(walletAddress));
            return "player-found";
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error-page";
        }
    }

}

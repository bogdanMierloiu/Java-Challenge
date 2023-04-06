package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
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

import java.util.List;

@Controller
@SessionAttributes("player")
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerMVCController {

    private final PlayerService playerService;
    private final WalletHistoryService walletHistoryService;

    private final NftService nftService;


    @GetMapping("/account")
    public String goToAccount(HttpSession session, Model model) {
        PlayerResponse player = (PlayerResponse) session.getAttribute("player");
        List<WalletHistoryResponse> walletHistory = walletHistoryService.findByWallet(player.getId());
        List<Nft> nftList = nftService.findByWallet(player.getWallet().getId());
        model.addAttribute("player", player);
        model.addAttribute("walletHistory", walletHistory);
        model.addAttribute("nftList", nftList);
        return "player-account";
    }

}

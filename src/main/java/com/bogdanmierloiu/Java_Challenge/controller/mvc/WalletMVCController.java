package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.TransferTokens;
import com.bogdanmierloiu.Java_Challenge.dto.wallet.WalletResponse;
import com.bogdanmierloiu.Java_Challenge.dto.wallet_history.WalletHistoryResponse;
import com.bogdanmierloiu.Java_Challenge.entity.Nft;
import com.bogdanmierloiu.Java_Challenge.exception.NotEnoughTokens;
import com.bogdanmierloiu.Java_Challenge.service.NftService;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import com.bogdanmierloiu.Java_Challenge.service.WalletHistoryService;
import com.bogdanmierloiu.Java_Challenge.service.WalletService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wallet")
@SessionAttributes("player")
@RequiredArgsConstructor
public class WalletMVCController {

    private final WalletService walletService;
    private final WalletHistoryService walletHistoryService;
    private final PlayerService playerService;

    private final NftService nftService;

    @GetMapping("/send-page/{senderWalletId}")
    public String showSendPage(@PathVariable("senderWalletId") String senderWalletId, Model model) {
        WalletResponse senderWallet = walletService.findById(Long.parseLong(senderWalletId));
        PlayerResponse senderPlayer = playerService.findByWalletId(Long.parseLong(senderWalletId));
        TransferTokens transfer = new TransferTokens();
        model.addAttribute("senderPlayer", senderPlayer);
        model.addAttribute("senderWallet", senderWallet);
        model.addAttribute("players", playerService.getAllByName());
        model.addAttribute("transfer", transfer);
        return "send-page";
    }

    @PostMapping("/send")
    public String send(@ModelAttribute TransferTokens transfer, Model model) {
        try {
            walletService.sendTokens(transfer.getSenderWalletId(), transfer.getReceiverWalletId(), transfer.getNrOfTokens());

            PlayerResponse playerSender = playerService.findByWalletId(transfer.getSenderWalletId());
            List<WalletHistoryResponse> walletHistory = walletHistoryService.findByWallet(playerSender.getId());
            model.addAttribute("player", playerSender);
            model.addAttribute("wallet", playerSender.getWallet());
            model.addAttribute("walletHistory", walletHistory);
            model.addAttribute("nftList", playerSender.getWallet().getNfts());
            return "player-account";
        } catch (NotEnoughTokens e) {
            WalletResponse senderWallet = walletService.findById(transfer.getSenderWalletId());
            PlayerResponse senderPlayer = playerService.findByWalletId(transfer.getSenderWalletId());
            TransferTokens newTransfer = new TransferTokens();
            model.addAttribute("senderPlayer", senderPlayer);
            model.addAttribute("senderWallet", senderWallet);
            model.addAttribute("players", playerService.getAllByName());
            model.addAttribute("transfer", newTransfer);
            model.addAttribute("errorMessage", e.getMessage());
            return "send-page";

        }


    }
}

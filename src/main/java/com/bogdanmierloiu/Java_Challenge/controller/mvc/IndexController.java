package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.security.AppUser;
import com.bogdanmierloiu.Java_Challenge.service.AnswerService;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import com.bogdanmierloiu.Java_Challenge.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping
@SessionAttributes("player")
@RequiredArgsConstructor
public class IndexController {
    private final PlayerService playerService;
    private final QuestionService questionService;

    @GetMapping
    public String goToIndex(Authentication authentication, Model model) {
        AdminController.checkUser(authentication, model, playerService);
        model.addAttribute("questions", questionService.findAllByIsResolvedFalse());
        return "index";
    }


}

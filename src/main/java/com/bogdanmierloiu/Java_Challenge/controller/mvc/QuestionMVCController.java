package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionRequest;
import com.bogdanmierloiu.Java_Challenge.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("player")
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionMVCController {

    private final QuestionService questionService;

    @GetMapping("/add-question-form")
    public String goToAddQuestion(HttpSession session, Model model) {
        model.addAttribute("player", session.getAttribute("player"));
        return "add-question-form";
    }

    @PostMapping("/add")
    public String addQuestion(HttpSession session, @ModelAttribute QuestionRequest questionRequest, Model model) {
        questionService.add(questionRequest);
        model.addAttribute("questions", questionService.findAllByIsResolvedFalse());
        return "index";
    }


}

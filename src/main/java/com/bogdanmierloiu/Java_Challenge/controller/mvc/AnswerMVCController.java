package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.answer.AnswerRequest;
import com.bogdanmierloiu.Java_Challenge.dto.player.PlayerResponse;
import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionResponse;
import com.bogdanmierloiu.Java_Challenge.security.AppUser;
import com.bogdanmierloiu.Java_Challenge.service.AnswerService;
import com.bogdanmierloiu.Java_Challenge.service.PlayerService;
import com.bogdanmierloiu.Java_Challenge.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/answer")
@SessionAttributes("player")
@RequiredArgsConstructor
public class AnswerMVCController {
    private final AnswerService answerService;
    private final PlayerService playerService;
    private final QuestionService questionService;

    @GetMapping("/add-answer-form/{id}")
    public String goToAddAnswer(Authentication authentication, @PathVariable("id") String id, Model model) {
        String name;
        try {
            name = ((AppUser) authentication.getPrincipal()).getAttribute("name");
        } catch (Exception e) {
            name = ((AppUser) authentication.getPrincipal()).getUsername();
        }
        PlayerResponse playerResponse = playerService.findByName(name);
        QuestionResponse question = questionService.findById(Long.parseLong(id));
        PlayerResponse questionOwner = question.getPlayer();
        model.addAttribute("playerName", playerResponse.getName());
        model.addAttribute("playerId", playerResponse.getId());
        model.addAttribute("question", question);
        model.addAttribute("questionOwner", questionOwner);
        model.addAttribute("answers", answerService.findByQuestion(Long.parseLong(id)));
        return "add-answer-form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute AnswerRequest answerRequest, Model model) {
        try {
            answerService.add(answerRequest);
            model.addAttribute("questions", questionService.findAllByIsResolvedFalse());
            return "index";
        } catch (DataIntegrityViolationException e){
            model.addAttribute("errorMessage", e.getMessage());
            QuestionResponse question = questionService.findById(answerRequest.getQuestionId());
            PlayerResponse questionOwner = question.getPlayer();
            model.addAttribute("playerId", answerRequest.getPlayerId());
            model.addAttribute("question", question);
            model.addAttribute("questionOwner", questionOwner);
            model.addAttribute("answers", answerService.findByQuestion(answerRequest.getQuestionId()));
            if(answerRequest.getText() != null) {
                model.addAttribute("previousAnswer", answerRequest.getText());
            }
            return "add-answer-form";
        }
    }

    @GetMapping("/validate/{answerId}/{playerId}/{questionId}")
    public String validate(@PathVariable("answerId") Long answerId,
                           @PathVariable("playerId") String playerId,
                           @PathVariable("questionId") Long questionId,
                           Authentication authentication,
                           Model model) {
        String name;
        try {
            name = ((AppUser) authentication.getPrincipal()).getAttribute("name");
        } catch (Exception e) {
            name = ((AppUser) authentication.getPrincipal()).getUsername();
        }
        PlayerResponse playerWhoTryToValidate = playerService.findByName(name);
        answerService.validateAnswer(answerId, Long.parseLong(playerId), questionId, playerWhoTryToValidate.getId());
        model.addAttribute("questions", questionService.findAllByIsResolvedFalse());
        return "index";
    }
}

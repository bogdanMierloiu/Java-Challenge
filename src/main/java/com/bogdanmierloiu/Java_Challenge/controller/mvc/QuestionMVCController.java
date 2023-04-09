package com.bogdanmierloiu.Java_Challenge.controller.mvc;

import com.bogdanmierloiu.Java_Challenge.dto.question.QuestionRequest;
import com.bogdanmierloiu.Java_Challenge.exception.NotEnoughTokens;
import com.bogdanmierloiu.Java_Challenge.service.AnswerService;
import com.bogdanmierloiu.Java_Challenge.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("player")
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionMVCController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("/add-question-form")
    public String goToAddQuestion(HttpSession session, Model model) {
        model.addAttribute("player", session.getAttribute("player"));
        return "add-question-form";
    }

    @PostMapping("/add")
    public String addQuestion(@ModelAttribute QuestionRequest questionRequest, Model model, HttpSession session) {
        try {
            questionService.addFromPlayer(questionRequest);
            model.addAttribute("questions", questionService.findAllByIsResolvedFalse());
            return "index";
        } catch (NotEnoughTokens | DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("questionRequest", questionRequest);
            model.addAttribute("player", session.getAttribute("player"));
            return "add-question-form";
        }
    }

    @GetMapping("/questions-for-player/{playerId}")
    public String viewAllQuestionsForPlayer(HttpSession session, Model model, @PathVariable("playerId") String playerId) {
        model.addAttribute("questions", questionService.findAllByPlayer(Long.parseLong(playerId)));
        model.addAttribute("player", session.getAttribute("player"));
        return "questions-for-player";
    }

    @GetMapping("/answers-for-question/{questionId}")
    public String viewAnswersForQuestion(@PathVariable("questionId") String questionId, Model model) {
        model.addAttribute("answers", answerService.findByQuestion(Long.parseLong(questionId)));
        model.addAttribute("question", questionService.findById(Long.parseLong(questionId)));
        return "answers-for-question";
    }


}

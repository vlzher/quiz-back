package com.quiz.quizback.controller;

import com.quiz.quizback.dto.*;
import com.quiz.quizback.service.QuizService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public List<QuizDTO> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping
    public ResponseEntity<CreateQuizDTO> createQuiz(@RequestBody CreateQuizRequest request) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String username = (String) jwt.getTokenAttributes().get("sub");
        String quizTitle = request.getQuizTitle();
        CreateQuizDTO createdQuiz = quizService.createQuiz(quizTitle,username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @GetMapping("/{quizID}")
    public ResponseEntity<QuizDTO> getQuizByID(@PathVariable String quizID) {
        return quizService.getQuizWithQuestions(quizID);
    }

    @PostMapping("/{quizID}/addOneChooseQuestion")
    public ResponseEntity<QuestionDTO> addOneChooseQuestion(@PathVariable String quizID, @RequestBody AddChooseQuestionRequest request) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String username = (String) jwt.getTokenAttributes().get("sub");
        return quizService.addChooseQuestion(quizID, request,username);
    }

    @PostMapping("/{quizID}/addMultipleChooseQuestion")
    public ResponseEntity<QuestionDTO> addMultipleChooseQuestion(@PathVariable String quizID, @RequestBody AddMultipleQuestionRequest request) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String username = (String) jwt.getTokenAttributes().get("sub");
        return quizService.addMultipleChooseQuestion(quizID, request,username);
    }

    @PostMapping("/{quizID}/addFileQuestion")
    public ResponseEntity<QuestionDTO> addFileQuestion(@PathVariable String quizID, @RequestBody AddFileQuestionRequest request) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String username = (String) jwt.getTokenAttributes().get("sub");
        return quizService.addFileQuestion(quizID, request,username);
    }
    @PostMapping("/{quizID}/addOrderQuestion")
    public ResponseEntity<QuestionDTO> addOrderQuestion(@PathVariable String quizID, @RequestBody AddOrderQuestionRequest request) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String username = (String) jwt.getTokenAttributes().get("sub");
        return quizService.addOrderQuestion(quizID, request,username);
    }
    @PostMapping("/{quizID}/addMatchQuestion")
    public ResponseEntity<QuestionDTO> addMatchQuestion(@PathVariable String quizID, @RequestBody AddMatchQuestionRequest request) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String username = (String) jwt.getTokenAttributes().get("sub");
        return quizService.addMatchQuestion(quizID, request,username);
    }

    @DeleteMapping("/{quizID}")
    public ResponseEntity<QuizDTO> deleteQuiz(@PathVariable String quizID) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String username = (String) jwt.getTokenAttributes().get("sub");
        return quizService.deleteQuiz(quizID,username);
    }

    @DeleteMapping("/{quizID}/{questionID}")
    public ResponseEntity<QuestionDTO> deleteQuestion(@PathVariable String quizID, @PathVariable String questionID) {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        String username = (String) jwt.getTokenAttributes().get("sub");
        return quizService.deleteQuestion(quizID, questionID,username);
    }



//    @PostMapping("/{quizID}/answers")
//    public void addAnswers(@PathVariable Long quizID) {
//        quizService.addAnswers(quizID);
//    }

//    @GetMapping("/{quizID}/answers")
//    public void getAnswers(@PathVariable Long quizID) {
//        quizService.addAnswers(quizID);
//    }
//    @GetMapping("/{quizID}/stats")
//    public QuizStats getStats(@PathVariable Long quizID) {
//        return quizService.getStats(quizID);
//    }
}

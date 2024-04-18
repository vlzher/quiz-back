package com.quiz.quizback.controller;

import com.quiz.quizback.dto.*;
import com.quiz.quizback.service.QuizService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        String quizTitle = request.getQuizTitle();
        CreateQuizDTO createdQuiz = quizService.createQuiz(quizTitle);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }

    @GetMapping("/{quizID}")
    public ResponseEntity<QuizDTO> getQuizByID(@PathVariable String quizID) {
        return quizService.getQuizWithQuestions(quizID);
    }

    @DeleteMapping("/{quizID}")
    public void removeQuiz(@PathVariable String quizID) {
        quizService.removeQuiz(quizID);
    }

    @PostMapping("/{quizID}/addQuestion")
    public ResponseEntity<QuestionDTO> addQuestion(@PathVariable String quizID, @RequestBody AddQuestionRequest request) {
        return quizService.addQuestion(quizID, request);
    }

    @DeleteMapping("/{quizID}/{questionID}")
    public void deleteQuestion(@PathVariable String quizID, @PathVariable String questionID) {
        quizService.deleteQuestion(quizID, questionID);
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

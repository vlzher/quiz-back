package com.quiz.quizback.dto;

import lombok.Data;

import java.util.List;
@Data
public class AnswerOrderQuestionRequest {
    private List<String> answer;
}

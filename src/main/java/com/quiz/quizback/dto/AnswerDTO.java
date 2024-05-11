package com.quiz.quizback.dto;

import lombok.Data;

import java.util.List;
@Data
public class AnswerDTO {
        private String id;
        private String questionID;
        private List<String> chooseAnswers;
        private List<String> orderAnswers;
        private List<List<String>> matchAnswers;
        private String chooseAnswer;
        private String fileName;
        private Boolean isCorrect;
}

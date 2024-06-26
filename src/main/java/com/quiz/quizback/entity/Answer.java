package com.quiz.quizback.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Answer {
    @Id
    private String id;
    private String userID;
    private String questionID;
    private List<String> chooseAnswers;
    private List<String> orderAnswers;
    private List<List<String>> matchAnswers;
    private String chooseAnswer;
    private String fileName;
    private byte[] file;
    private Boolean isCorrect;

}


package com.quiz.quizback.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Question {
    @Id
    private String id;
    private String text;
    private QuestionType type;
    private String quizID;
    private List<String> correctOptions;
    private List<String> correctOrder;
    private List<List<String>> correctMatch;
    private String correctChooseOption;
}

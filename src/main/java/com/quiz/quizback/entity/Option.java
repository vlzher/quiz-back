package com.quiz.quizback.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Option {
    @Id
    private String id;
    private String text;
    private Boolean isLeft;
    private String questionID;

}

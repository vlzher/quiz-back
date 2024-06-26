package com.quiz.quizback.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Quiz {
    @Id
    private String id;
    private String title;
    private String userID;

}

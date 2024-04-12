package com.quiz.quizback.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private int correctOrder;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}

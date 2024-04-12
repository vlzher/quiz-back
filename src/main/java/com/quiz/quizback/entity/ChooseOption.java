package com.quiz.quizback.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ChooseOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}

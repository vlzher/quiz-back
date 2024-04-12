package com.quiz.quizback.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private QuestionType type;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<OrderOption> orderOptions;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<MatchOption> matchOptions;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<FileOption> fileOptions;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<ChooseOption> chooseOptions;

}

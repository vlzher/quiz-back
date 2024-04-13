package com.quiz.quizback.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "choose_option_id")
    private ChooseOption chooseOption;

    @ManyToOne
    @JoinColumn(name = "order_option_id")
    private OrderOption orderOption;

    @ManyToOne
    @JoinColumn(name = "file_option_id")
    private FileOption fileOption;

    @ManyToOne
    @JoinColumn(name = "match_option_id")
    private MatchOption matchOption;
}

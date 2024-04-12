package com.quiz.quizback.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FileOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] file;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

}

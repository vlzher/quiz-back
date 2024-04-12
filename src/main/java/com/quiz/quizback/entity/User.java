package com.quiz.quizback.entity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "quiz_user")
public class User {
    @Id
    private String username;
    private String email;
    private String provider;
}

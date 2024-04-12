package com.quiz.quizback.repository;

import com.quiz.quizback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

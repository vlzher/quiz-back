package com.quiz.quizback.repository;
import com.quiz.quizback.entity.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface QuizRepository extends MongoRepository<Quiz, String> {
}
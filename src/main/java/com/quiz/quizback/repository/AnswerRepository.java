package com.quiz.quizback.repository;
import com.quiz.quizback.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface AnswerRepository extends MongoRepository<Answer, String> {
}

package com.quiz.quizback.repository;
import com.quiz.quizback.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByQuizID(String quizId);
    void deleteByQuizID(String quizId);
}

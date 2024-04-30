package com.quiz.quizback.repository;
import com.quiz.quizback.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnswerRepository extends MongoRepository<Answer, String> {
    Answer findByQuestionIDAndUserID(String questionID, String userID);
    List<Answer> findAllByQuestionID(String questionID);
    void deleteAllByQuestionID(String questionID);
}

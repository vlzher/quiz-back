package com.quiz.quizback.repository;

import com.quiz.quizback.entity.Option;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OptionRepository extends MongoRepository<Option, String> {
    List<Option> findByQuestionID(String questionID);

    void deleteByQuestionID(String questionID);
}

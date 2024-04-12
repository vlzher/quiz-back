package com.quiz.quizback.repository;

import com.quiz.quizback.entity.ChooseOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChooseOptionRepository extends JpaRepository<ChooseOption, Long> {
    List<ChooseOption> findByQuestionId(Long questionId);
    @Transactional
    void deleteByQuestionId(Long questionId);
}

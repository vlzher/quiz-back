package com.quiz.quizback.repository;

import com.quiz.quizback.entity.MatchOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MatchOptionRepository extends JpaRepository<MatchOption, Long> {
    List<MatchOption> findByQuestionId(Long questionId);
    @Transactional
    void deleteByQuestionId(Long questionId);
}

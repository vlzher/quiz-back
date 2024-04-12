package com.quiz.quizback.repository;

import com.quiz.quizback.entity.OrderOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderOptionRepository extends JpaRepository<OrderOption, Long> {
    List<OrderOption> findByQuestionId(Long questionId);
    @Transactional
    void deleteByQuestionId(Long questionId);
}

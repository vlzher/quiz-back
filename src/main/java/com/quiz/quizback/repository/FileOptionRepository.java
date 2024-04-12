package com.quiz.quizback.repository;

import com.quiz.quizback.entity.FileOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileOptionRepository extends JpaRepository<FileOption, Long> {
    List<FileOption> findByQuestionId(Long questionId);
    @Transactional
    void deleteByQuestionId(Long questionId);
}

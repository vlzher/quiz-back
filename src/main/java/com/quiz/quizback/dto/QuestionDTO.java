package com.quiz.quizback.dto;

import lombok.Data;

import java.util.List;
import com.quiz.quizback.entity.QuestionType;

@Data
public class QuestionDTO {
    private String id;
    private String text;
    private QuestionType type;
    private List<OptionDTO> options;
}

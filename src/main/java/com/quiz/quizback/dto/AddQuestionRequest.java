package com.quiz.quizback.dto;

import com.quiz.quizback.entity.QuestionType;
import lombok.Data;

import java.util.List;
@Data
public class AddQuestionRequest {
    private String text;
    private QuestionType type;
    private List<OptionDTO> options;
}

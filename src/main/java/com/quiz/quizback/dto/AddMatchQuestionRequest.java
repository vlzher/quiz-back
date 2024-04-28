package com.quiz.quizback.dto;

import com.quiz.quizback.entity.QuestionType;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class AddMatchQuestionRequest {
    private String text;
    private QuestionType type;
    private List<OptionDTO> leftOptions;
    private List<OptionDTO> rightOptions;
    private List<List<Integer>> correctAnswer;
}

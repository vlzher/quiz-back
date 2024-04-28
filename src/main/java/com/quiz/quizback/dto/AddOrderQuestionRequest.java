package com.quiz.quizback.dto;

import com.quiz.quizback.entity.QuestionType;
import lombok.Data;

import java.util.List;
@Data
public class AddOrderQuestionRequest {
    private String text;
    private List<OptionDTO> options;
    private List<Integer> correctOrder;
}

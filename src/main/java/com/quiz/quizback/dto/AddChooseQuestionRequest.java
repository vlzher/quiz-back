package com.quiz.quizback.dto;

import com.quiz.quizback.entity.QuestionType;
import lombok.Data;

import java.util.List;
@Data
public class AddChooseQuestionRequest {
    private String text;
    private List<OptionDTO> options;
    private int correctOptionPosition;
}

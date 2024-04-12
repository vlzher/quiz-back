package com.quiz.quizback.dto;

import lombok.Data;

import java.util.List;
import com.quiz.quizback.entity.QuestionType;

@Data
public class QuestionDTO {
    private Long id;
    private String text;
    private QuestionType type;
    private List<ChooseOptionDTO> chooseOptions;
    private List<OrderOptionDTO> orderOptions;
    private List<MatchOptionDTO> matchOptions;
    private List<FileOptionDTO> fileOptions;
}

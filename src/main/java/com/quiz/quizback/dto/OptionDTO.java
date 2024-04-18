package com.quiz.quizback.dto;

import lombok.Data;

@Data
public class OptionDTO {
    private String id;
    private String text;
    private Boolean isLeft;
    private String questionID;


}

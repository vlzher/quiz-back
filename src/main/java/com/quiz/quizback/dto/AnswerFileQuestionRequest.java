package com.quiz.quizback.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AnswerFileQuestionRequest {
    private MultipartFile file;
}

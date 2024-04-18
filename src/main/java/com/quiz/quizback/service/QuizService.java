package com.quiz.quizback.service;

import com.quiz.quizback.dto.*;
import com.quiz.quizback.entity.*;
import com.quiz.quizback.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;


    @Autowired
    private ModelMapper modelMapper;

    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(this::mapQuizToDTO)
                .collect(Collectors.toList());
    }

    public CreateQuizDTO createQuiz(String quizTitle) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz = quizRepository.save(quiz);
        return modelMapper.map(quiz, CreateQuizDTO.class);
    }

    public ResponseEntity<QuizDTO> getQuizWithQuestions(String quizID) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Quiz quiz = quizOptional.get();
        QuizDTO quizDTO = mapQuizToDTO(quiz);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questionRepository.findByQuizID(quizID)) {
            QuestionDTO questionDTO = mapQuestionToDTO(question);
            questionDTOs.add(questionDTO);
        }
        quizDTO.setQuestions(questionDTOs);
        return ResponseEntity.ok(quizDTO);
    }

    public void removeQuiz(String quizID) {
        quizRepository.deleteById(quizID);
    }

    private QuizDTO mapQuizToDTO(Quiz quiz) {
        return modelMapper.map(quiz, QuizDTO.class);
    }

    private QuestionDTO mapQuestionToDTO(Question question) {
        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);
        List<OptionDTO> options = optionRepository.findByQuestionID(question.getId())
                .stream()
                .map(chooseOption -> modelMapper.map(chooseOption, OptionDTO.class))
                .collect(Collectors.toList());
        questionDTO.setOptions(options);
        return questionDTO;
    }

    public ResponseEntity<QuestionDTO> addQuestion(String quizID, AddQuestionRequest request) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Question question = new Question();
        question.setText(request.getText());
        question.setType(request.getType());
        question.setQuizID(quizID);
        question = questionRepository.save(question);


        List<OptionDTO> chooseOptions = request.getOptions();
        if (chooseOptions != null && !chooseOptions.isEmpty()) {
            for (OptionDTO chooseOptionDTO : chooseOptions) {
                Option option = new Option();
                option.setText(chooseOptionDTO.getText());
                option.setQuestionID(question.getId());
                if(chooseOptionDTO.getIsLeft() != null){
                    option.setIsLeft(chooseOptionDTO.getIsLeft());
                }
                optionRepository.save(option);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(mapQuestionToDTO(question));
    }

    public void deleteQuestion(String quizID, String questionID) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            ResponseEntity.notFound().build();
            return;
        }
        Optional<Question> questionOptional = questionRepository.findById(questionID);
        if (questionOptional.isEmpty()) {
            ResponseEntity.notFound().build();
            return;
        }
        optionRepository.deleteByQuestionID(questionID);
        questionRepository.deleteById(questionID);
        ResponseEntity.noContent().build();
    }



    public void addAnswers(Long quizID) {
        // Implementation to add answers to a quiz
    }

//    public QuizStats getStats(Long quizID) {
//        // Implementation to retrieve statistics for a quiz
//        return null;
//    }
}

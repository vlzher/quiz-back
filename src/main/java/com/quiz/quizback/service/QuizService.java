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
    private ChooseOptionRepository chooseOptionRepository;

    @Autowired
    private OrderOptionRepository orderOptionRepository;

    @Autowired
    private MatchOptionRepository matchOptionRepository;

    @Autowired
    private FileOptionRepository fileOptionRepository;

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

    public ResponseEntity<QuizDTO> getQuizWithQuestions(Long quizID) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Quiz quiz = quizOptional.get();
        QuizDTO quizDTO = mapQuizToDTO(quiz);
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : quiz.getQuestions()) {
            QuestionDTO questionDTO = mapQuestionToDTO(question);
            questionDTOs.add(questionDTO);
        }
        quizDTO.setQuestions(questionDTOs);
        return ResponseEntity.ok(quizDTO);
    }

    public void removeQuiz(Long quizID) {
        quizRepository.deleteById(quizID);
    }

    private QuizDTO mapQuizToDTO(Quiz quiz) {
        return modelMapper.map(quiz, QuizDTO.class);
    }

    private QuestionDTO mapQuestionToDTO(Question question) {
        QuestionDTO questionDTO = modelMapper.map(question, QuestionDTO.class);

        List<ChooseOptionDTO> chooseOptions = chooseOptionRepository.findByQuestionId(question.getId())
                .stream()
                .map(chooseOption -> modelMapper.map(chooseOption, ChooseOptionDTO.class))
                .collect(Collectors.toList());
        questionDTO.setChooseOptions(chooseOptions);

        List<OrderOptionDTO> orderOptions = orderOptionRepository.findByQuestionId(question.getId())
                .stream()
                .map(orderOption -> modelMapper.map(orderOption, OrderOptionDTO.class))
                .collect(Collectors.toList());
        questionDTO.setOrderOptions(orderOptions);

        List<MatchOptionDTO> matchOptions = matchOptionRepository.findByQuestionId(question.getId())
                .stream()
                .map(matchOption -> modelMapper.map(matchOption, MatchOptionDTO.class))
                .collect(Collectors.toList());
        questionDTO.setMatchOptions(matchOptions);

        List<FileOptionDTO> fileOptions = fileOptionRepository.findByQuestionId(question.getId())
                .stream()
                .map(fileOption -> modelMapper.map(fileOption, FileOptionDTO.class))
                .collect(Collectors.toList());
        questionDTO.setFileOptions(fileOptions);

        return questionDTO;
    }

    public ResponseEntity<QuestionDTO> addQuestion(Long quizID, AddQuestionRequest request) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Quiz quiz = quizOptional.get();

        Question question = new Question();
        question.setText(request.getText());
        question.setType(request.getType());
        question.setQuiz(quiz);
        question = questionRepository.save(question);

        List<ChooseOptionDTO> chooseOptions = request.getChooseOptions();
        if (chooseOptions != null && !chooseOptions.isEmpty()) {
            for (ChooseOptionDTO chooseOptionDTO : chooseOptions) {
                ChooseOption chooseOption = new ChooseOption();
                chooseOption.setText(chooseOptionDTO.getText());
                chooseOption.setCorrect(chooseOptionDTO.isCorrect());
                chooseOption.setQuestion(question);
                chooseOptionRepository.save(chooseOption);
            }
        }
        List<OrderOptionDTO> orderOptions = request.getOrderOptions();
        if (orderOptions != null && !orderOptions.isEmpty()) {
            for (OrderOptionDTO orderOptionDTO : orderOptions) {
                OrderOption orderOption = new OrderOption();
                orderOption.setText(orderOptionDTO.getText());
                orderOption.setCorrectOrder(orderOptionDTO.getCorrectOrder());
                orderOption.setQuestion(question);
                orderOptionRepository.save(orderOption);
            }
        }

        List<MatchOptionDTO> matchOptions = request.getMatchOptions();
        if (matchOptions != null && !matchOptions.isEmpty()) {
            for (MatchOptionDTO matchOptionDTO : matchOptions) {
                MatchOption matchOption = new MatchOption();
                matchOption.setText(matchOptionDTO.getText());
                matchOption.setLeft(matchOptionDTO.isLeft());
                matchOption.setCorrectAnswer(matchOptionDTO.getCorrectAnswer());
                matchOption.setQuestion(question);
                matchOptionRepository.save(matchOption);
            }
        }

        List<FileOptionDTO> fileOptions = request.getFileOptions();
        if (fileOptions != null && !fileOptions.isEmpty()) {
            for (FileOptionDTO fileOptionDTO : fileOptions) {
                FileOption fileOption = new FileOption();
                fileOption.setFile(fileOptionDTO.getFile());
                fileOption.setQuestion(question);
                fileOptionRepository.save(fileOption);
            }
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(mapQuestionToDTO(question));
    }

    public void deleteQuestion(Long quizID, Long questionID) {
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
        deleteOptionsByQuestionID(questionID);
        questionRepository.deleteById(questionID);
        ResponseEntity.noContent().build();
    }

    private void deleteOptionsByQuestionID(Long questionID) {
        chooseOptionRepository.deleteByQuestionId(questionID);
        orderOptionRepository.deleteByQuestionId(questionID);
        matchOptionRepository.deleteByQuestionId(questionID);
        fileOptionRepository.deleteByQuestionId(questionID);
    }

    public void addAnswers(Long quizID) {
        // Implementation to add answers to a quiz
    }

//    public QuizStats getStats(Long quizID) {
//        // Implementation to retrieve statistics for a quiz
//        return null;
//    }
}

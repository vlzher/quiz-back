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
import java.util.Map;
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

    public CreateQuizDTO createQuiz(String quizTitle, String username) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz.setUserID(username);
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

    public boolean checkQuizOwner(String quizID,String username){
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return false;
        }
        Quiz quiz = quizOptional.get();
        return quiz.getUserID().equals(username);
    }

    public ResponseEntity<QuizDTO> removeQuiz(String quizID,String username) {
        if(checkQuizOwner(quizID,username)){
            quizRepository.deleteById(quizID);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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

    public ResponseEntity<QuestionDTO> addChooseQuestion(String quizID, AddChooseQuestionRequest request, String username) {
        if(!checkQuizOwner(quizID,username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Question question = new Question();
        question.setText(request.getText());
        question.setType(QuestionType.ONE);
        question.setQuizID(quizID);
        question = questionRepository.save(question);
        List<OptionDTO> options = request.getOptions();
        String questionID = question.getId();
        options.forEach(optionDTO -> {
            Option option = modelMapper.map(optionDTO, Option.class);
            option.setQuestionID(questionID);
            optionRepository.save(option);
        });
        int correctOptionPosition = request.getCorrectOptionPosition();
        OptionDTO correctOption = options.get(correctOptionPosition);
        String optionID = optionRepository.findByQuestionID(questionID)
                .stream()
                .filter(optionTemp -> optionTemp.getText().equals(correctOption.getText()))
                .findFirst()
                .get()
                .getId();
        question.setCorrectChooseOption(optionID);
        question = questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapQuestionToDTO(question));
    }
    public ResponseEntity<QuestionDTO> addMultipleChooseQuestion(String quizID, AddMultipleQuestionRequest request, String username){
        if(!checkQuizOwner(quizID,username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Question question = new Question();
        question.setText(request.getText());
        question.setType(QuestionType.MULTIPLE);
        question.setQuizID(quizID);
        question = questionRepository.save(question);
        List<OptionDTO> options = request.getOptions();
        String questionID = question.getId();
        options.forEach(optionDTO -> {
            Option option = modelMapper.map(optionDTO, Option.class);
            option.setQuestionID(questionID);
            optionRepository.save(option);
        });
        List<Integer> correctOptionPositions = request.getCorrectOptionPositions();
        List<String> correctOptionIds = new ArrayList<>();
        correctOptionPositions.forEach(position -> {
            OptionDTO correctOption = options.get(position);
            String optionID = optionRepository.findByQuestionID(questionID)
                    .stream()
                    .filter(optionTemp -> optionTemp.getText().equals(correctOption.getText()))
                    .findFirst()
                    .get()
                    .getId();
            correctOptionIds.add(optionID);
        });
        question.setCorrectChooseOptions(correctOptionIds);
        question = questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapQuestionToDTO(question));
    }

    public ResponseEntity<QuestionDTO> addFileQuestion(String quizID, AddFileQuestionRequest request, String username) {
        if(!checkQuizOwner(quizID,username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Question question = new Question();
        question.setText(request.getText());
        question.setType(QuestionType.FILE);
        question.setQuizID(quizID);
        question = questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(question, QuestionDTO.class));
    }

    public ResponseEntity<QuestionDTO> addOrderQuestion(String quizID, AddOrderQuestionRequest request, String username){
        if(!checkQuizOwner(quizID,username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Question question = new Question();
        question.setText(request.getText());
        question.setType(QuestionType.ORDER);
        question.setQuizID(quizID);
        question = questionRepository.save(question);
        List<OptionDTO> options = request.getOptions();
        String questionID = question.getId();
        options.forEach(optionDTO -> {
            Option option = modelMapper.map(optionDTO, Option.class);
            option.setQuestionID(questionID);
            optionRepository.save(option);
        });
        List<Integer> correctOrder = request.getCorrectOrder();
        List<String> correctOptionIds = new ArrayList<>();
        correctOrder.forEach(position -> {
            OptionDTO correctOption = options.get(position);
            String optionID = optionRepository.findByQuestionID(questionID)
                    .stream()
                    .filter(optionTemp -> optionTemp.getText().equals(correctOption.getText()))
                    .findFirst()
                    .get()
                    .getId();
            correctOptionIds.add(optionID);
        });
        question.setCorrectOrder(correctOptionIds);
        question = questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapQuestionToDTO(question));
    }

    public ResponseEntity<QuestionDTO> addMatchQuestion(String quizID, AddMatchQuestionRequest request, String username){
        if(!checkQuizOwner(quizID,username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Question question = new Question();
        question.setText(request.getText());
        question.setType(QuestionType.MATCH);
        question.setQuizID(quizID);
        question = questionRepository.save(question);
        List<OptionDTO> leftOptions = request.getLeftOptions();
        String questionID = question.getId();
        leftOptions.forEach(optionDTO -> {
            Option option = modelMapper.map(optionDTO, Option.class);
            option.setQuestionID(questionID);
            optionRepository.save(option);
        });
        List<OptionDTO> rightOptions = request.getRightOptions();
        rightOptions.forEach(optionDTO -> {
            Option option = modelMapper.map(optionDTO, Option.class);
            option.setQuestionID(questionID);
            optionRepository.save(option);
        });
        List<List<Integer>> correctAnswer  = request.getCorrectAnswer();
        List<List<String>> correctAnswerIds = new ArrayList<>();

        correctAnswer.forEach(positions -> {
            List<String> correctOptionId = new ArrayList<>();
            OptionDTO leftOption = leftOptions.get(positions.getFirst());
            OptionDTO rightOption = rightOptions.get(positions.get(1));
            String leftOptionID = optionRepository.findByQuestionID(questionID)
                    .stream()
                    .filter(optionTemp -> optionTemp.getText().equals(leftOption.getText()))
                    .findFirst()
                    .get()
                    .getId();
            String rightOptionID = optionRepository.findByQuestionID(questionID)
                    .stream()
                    .filter(optionTemp -> optionTemp.getText().equals(rightOption.getText()))
                    .findFirst()
                    .get()
                    .getId();
            correctOptionId.add(leftOptionID);
            correctOptionId.add(rightOptionID);
            correctAnswerIds.add(correctOptionId);
        });
        question.setCorrectMatch(correctAnswerIds);;
        return ResponseEntity.status(HttpStatus.CREATED).body(mapQuestionToDTO(question));
    }


    public ResponseEntity<QuestionDTO> deleteQuestion(String quizID, String questionID,String username) {
        if(!checkQuizOwner(quizID,username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Quiz> quizOptional = quizRepository.findById(quizID);
        if (quizOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Question> questionOptional = questionRepository.findById(questionID);
        if (questionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        optionRepository.deleteByQuestionID(questionID);
        questionRepository.deleteById(questionID);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<QuizDTO> deleteQuiz(String quizID,String username) {
        if(!checkQuizOwner(quizID,username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Question> questions = questionRepository.findByQuizID(quizID);
        questions.forEach(question -> {
            optionRepository.deleteByQuestionID(question.getId());
        });
        questionRepository.deleteByQuizID(quizID);
        quizRepository.deleteById(quizID);

        return ResponseEntity.noContent().build();
    }
}


//    public void addAnswers(Long quizID) {
        // Implementation to add answers to a quiz
//    }

//    public QuizStats getStats(Long quizID) {
//        // Implementation to retrieve statistics for a quiz
//        return null;
//    }
//}

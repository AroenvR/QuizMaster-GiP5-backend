package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import be.ucll.quizmaster.quizmaster.service.QuestionService;
import be.ucll.quizmaster.quizmaster.service.QuizService;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("questions")
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping()
    private ResponseEntity<?> createQuestion(@RequestBody CreateQuestionDTO dto){
        logger.debug("POST question called.");

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(questionService.saveQuestion(dto));

        } catch (NotAuthenticatedException e) {
            logger.debug(e.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        catch (Exception e) {
            logger.debug(e.toString());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}

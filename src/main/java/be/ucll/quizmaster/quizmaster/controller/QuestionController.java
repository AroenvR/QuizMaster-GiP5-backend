package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.controller.dto.AnswerDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.ChooseQuestionDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import be.ucll.quizmaster.quizmaster.model.Answer;
import be.ucll.quizmaster.quizmaster.service.QuestionService;
import be.ucll.quizmaster.quizmaster.service.QuizService;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import be.ucll.quizmaster.quizmaster.service.exceptions.QuizFinishedException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Set;

@RestController
@RequestMapping("questions")
public class QuestionController {

    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping()
    private ResponseEntity<?> createQuestion(@RequestBody CreateQuestionDTO dto) {
        logger.debug("POST question called.");

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(questionService.saveQuestion(dto));

        } catch (NotAuthenticatedException e) {
            logger.debug(e.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            logger.debug(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping()
    private ResponseEntity<?> getNextQuestion(@RequestParam(name = "get-next") String answerToPrevious) {
        logger.debug("GET next question called. with -> " + answerToPrevious);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getNextQuestion(answerToPrevious));
        } catch (NotAuthenticatedException e) {
            logger.debug(e.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (QuizFinishedException e) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
        } catch (Exception e) {
            logger.warn(e.toString());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/mobile")
    private ResponseEntity<?> getNextQuestion(@RequestBody AnswerDTO answerToPrevious) {
        logger.debug("GET next mobile question called.");
        return getNextQuestion(answerToPrevious.getAnswer());
    }

    @GetMapping("/{topic}")
    public ResponseEntity<?> getAllForTopic(@PathVariable String topic) {
        logger.debug("Requested topic: " + topic);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionsByTopic(topic));

        } catch (NotAuthenticatedException e) {
            logger.debug(e.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e) {
            logger.debug(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

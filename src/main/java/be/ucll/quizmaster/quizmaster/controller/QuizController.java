package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuizDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.QuizDTO;
import be.ucll.quizmaster.quizmaster.service.QuizService;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("quizzes")
public class QuizController {


    private final QuizService quizService;

    private final Logger logger = LoggerFactory.getLogger(QuizController.class);

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping()
    private ResponseEntity<?> createQuiz(@RequestBody CreateQuizDTO dto) {
        logger.debug("POST quiz called.");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(quizService.saveQuiz(dto));
        } catch (NotAuthenticatedException e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping()
    private ResponseEntity<?> joinQuiz(@RequestParam(name = "code") String code) {

        logger.debug("JOIN quiz called.");
        try {
            ResponseEntity<QuizDTO> response = ResponseEntity.status(HttpStatus.CREATED).body(quizService.joinQuiz(code));
            logger.debug(response.toString());
            return response;
        } catch (NotAuthenticatedException e) {
            logger.info(e.toString());
            ResponseEntity<String> response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            logger.debug(response.toString());
            return response;
        } catch (Exception e) {
            logger.info(e.toString());
            ResponseEntity<String> body = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            logger.debug(body.toString());
            return body;
        }

    }

}

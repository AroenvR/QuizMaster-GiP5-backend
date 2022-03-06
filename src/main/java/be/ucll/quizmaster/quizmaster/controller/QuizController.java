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
    private ResponseEntity<?> createQuiz(@RequestBody CreateQuizDTO dto){
        logger.debug("POST quiz called.");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(quizService.saveQuiz(dto));
        } catch (NotAuthenticatedException e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        catch (Exception e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

/*    @PostMapping()
    private ResponseEntity<?> joinQuiz(@RequestParam String quizCode){

        logger.debug("JOIN quiz called.");
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(quizService.joinQuiz(quizCode));
        } catch (NotAuthenticatedException e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        catch (Exception e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }*/


}

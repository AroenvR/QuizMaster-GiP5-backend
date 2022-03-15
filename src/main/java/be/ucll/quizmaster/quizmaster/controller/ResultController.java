package be.ucll.quizmaster.quizmaster.controller;

import be.ucll.quizmaster.quizmaster.service.ResultService;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("results")
public class ResultController {

    private final Logger logger = LoggerFactory.getLogger(ResultController.class);

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/host")
    private ResponseEntity<?> getAllResultsForQuiz(@RequestParam(name = "code") String code){
        logger.debug("GET All Results For Quiz Called. -> " + code);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(resultService.getAllResultsForQuiz(code));
        } catch (NotAuthenticatedException e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping
    private ResponseEntity<?> getResult(@RequestParam(name = "code") String code){
        logger.debug("GET Result For Quiz Called ->." + code);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(resultService.getResultForQuiz(code));
        } catch (NotAuthenticatedException e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            logger.info(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }




}

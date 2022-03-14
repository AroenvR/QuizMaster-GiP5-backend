package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ResultService {

    private final Logger logger = LoggerFactory.getLogger(ResultService.class);

    private final ResultRepo resultRepo;
    private final QuizQuestionService quizQuestionService;

    public ResultService(ResultRepo resultRepo, QuizQuestionService quizQuestionService) {
        this.resultRepo = resultRepo;
        this.quizQuestionService = quizQuestionService;
    }

    public Result saveResult(Result result) {

        //checkResult(result);

        return resultRepo.save(result);

    }

    public Result getResultBy(QuizQuestion quizQuestion, Participant participant) {

        return resultRepo.getByQuizQuestionAndParticipant(quizQuestion, participant).orElseThrow();


    }

    @Transactional
    public void saveAnswerGiven(String answerToPrevious, Quiz quizPlayed, Participant currentParticipation) {

        QuizQuestion q = quizQuestionService.findQuizQuestionToBeAnswered(quizPlayed, currentParticipation);
        logger.debug("the previous question we have send this user was " + q.getQuestion().getQuestionString() + " from quiz with code " +  q.getQuiz().getCode());

        Result resultToUpdate = getResultBy(q, currentParticipation);

        resultToUpdate.setEndTime(new Date());
        resultToUpdate.setAnswerGiven(answerToPrevious);
        resultToUpdate.setIsCorrect(isCorrectAnswer(q.getQuestion(), answerToPrevious));

        logger.info("UPDATED Result -> " + resultToUpdate.toString());

    }

    private boolean isCorrectAnswer(Question question, String answerGiven) {
        for (Answer a : question.getAnswers()) {

            if (a.getAnswerString().strip().equalsIgnoreCase(answerGiven.strip()) && a.isCorrect()){
                return true;
            }
        }
        return false;
    }

}

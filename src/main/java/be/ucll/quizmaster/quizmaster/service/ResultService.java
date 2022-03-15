package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.controller.dto.FeedbackDTO;
import be.ucll.quizmaster.quizmaster.model.*;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
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
    private final LoginService loginService;
    private final ParticipantService participantService;
    private final QuizService quizService;

    public ResultService(ResultRepo resultRepo, QuizQuestionService quizQuestionService, LoginService loginService, ParticipantService participantService, QuizService quizService) {
        this.resultRepo = resultRepo;
        this.quizQuestionService = quizQuestionService;
        this.loginService = loginService;
        this.participantService = participantService;
        this.quizService = quizService;
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
        logger.debug("the previous question we have send this user was " + q.getQuestion().getQuestionString() + " from quiz with code " + q.getQuiz().getCode());

        Result resultToUpdate = getResultBy(q, currentParticipation);

        resultToUpdate.setEndTime(new Date());
        if (answerToPrevious == null || answerToPrevious.strip().equals("")) {
            resultToUpdate.setAnswerGiven("NO ANSWER GIVEN");
            resultToUpdate.setIsCorrect(false);
        } else {
            resultToUpdate.setAnswerGiven(answerToPrevious);
            resultToUpdate.setIsCorrect(isCorrectAnswer(q.getQuestion(), answerToPrevious));

        }

        logger.info("UPDATED Result -> " + resultToUpdate.toString());

    }

    private boolean isCorrectAnswer(Question question, String answerGiven) {
        for (Answer a : question.getAnswers()) {

            if (a.getAnswerString().strip().equalsIgnoreCase(answerGiven.strip()) && a.isCorrect()) {
                return true;
            }
        }
        return false;
    }

    public Object getAllResultsForQuiz(String code) throws NotAuthenticatedException {
        return null; //TODO
    }

    public FeedbackDTO getResultForQuiz(String code) throws NotAuthenticatedException {


        Member loggedInMember = loginService.getLoggedInMember("only members can get there result");

        if (code == null || code.strip().equals("")) {
            throw new IllegalArgumentException("no quiz code given.");
        }

        Quiz quizPlayed = quizService.getQuizByCode(code);

        if (!participantService.isAlreadyInThisQuiz(loggedInMember, quizPlayed)){
            throw new IllegalArgumentException("you did not participate in this quiz.");
        }

        if (participantService.getParticipation(quizPlayed, loggedInMember).isFinished()){
            throw new IllegalArgumentException("you need to finish a quiz before you can get your result.");
        }



        return null;
    }
}

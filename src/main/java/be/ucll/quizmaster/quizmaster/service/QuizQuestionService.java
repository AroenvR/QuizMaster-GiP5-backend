package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.*;
import be.ucll.quizmaster.quizmaster.repo.QuestionRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.QuizFinishedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class QuizQuestionService {

    private final Logger logger = LoggerFactory.getLogger(QuizQuestionService.class);

    private final QuestionRepo questionRepo;
    private final ResultRepo resultRepo;
    private final LoginService loginService;

    public QuizQuestionService(QuestionRepo questionRepo, ResultRepo resultRepo, LoginService loginService) {
        this.questionRepo = questionRepo;
        this.resultRepo = resultRepo;
        this.loginService = loginService;
    }


    public List<QuizQuestion> addQuestionsToQuizById(Quiz quiz, Set<Long> questionIds, Member host) {

        List<QuizQuestion> response = new ArrayList<>();

        for (Long questionId : questionIds) {

            Question question = questionRepo.findById(questionId).orElseThrow(
                    () ->  new EntityNotFoundException("no question found with id: " + questionId)
            );

            if (!question.getMember().equals(host)){
                throw new IllegalArgumentException("You don't have access to question '" + question.getQuestionString() + "', you can only add questions you created.");
            }

            response.add(new QuizQuestion(question, quiz));
            logger.info(question.getQuestionString() + " add to " + quiz.getTitle());

        }

        return response;

    }


    public QuizQuestion findQuizQuestionToBeAnswered(Quiz quiz, Participant participant) {

        for (QuizQuestion qq : quiz.getQuizQuestions()) {
            if (quizQuestionIsUnanswered(qq, participant) ){
                return qq;
            }
        }
        throw new RuntimeException("TODO");

    }

    public boolean isFirstQuestionFromQuiz(Quiz quizPlayed, Participant participation) {

        for (QuizQuestion qq : quizPlayed.getQuizQuestions()) {
            if (resultRepo.existsByQuizQuestionAndParticipant(qq, participation)){
                return false;
            }
        }
        return true;

    }

    public QuizQuestion findQuizQuestionToBeStarted(Quiz quiz, Participant participant) {

        for (QuizQuestion qq : quiz.getQuizQuestions()) {
            if (!resultRepo.existsByQuizQuestionAndParticipant(qq, participant) ){
                return qq;
            }
        }
        throw new QuizFinishedException("You have finished this quiz.");

    }

    public boolean isBreak(Quiz quiz, Participant participant) {

        int numberOfQuestionSend = getNumberOfQuestionsSend(quiz, participant);

        int numberOfQuestionsUnanswered = getNumberOfQuestionNotAnswered(quiz, participant);

        return numberOfQuestionSend % 5 == 0 && numberOfQuestionsUnanswered == 1;

    }


    public boolean isAfterBreak(Quiz quiz, Participant participant) {

        int numberOfQuestionSend = getNumberOfQuestionsSend(quiz, participant);

        int numberOfQuestionsUnanswered = getNumberOfQuestionNotAnswered(quiz, participant);

        return numberOfQuestionSend % 5 == 0 && numberOfQuestionsUnanswered == 0;

    }


    private int getNumberOfQuestionsSend(Quiz quiz, Participant participant) {
        int response = 0;
        for (QuizQuestion qq : quiz.getQuizQuestions()) {
            if (resultRepo.existsByQuizQuestionAndParticipant(qq, participant)) response++;
        }
        return response;
    }

    private int getNumberOfQuestionNotAnswered(Quiz quiz, Participant participant) {
        int response = 0;
        for (QuizQuestion qq : quiz.getQuizQuestions()) {
            if (resultRepo.existsByQuizQuestionAndParticipantAndAnswerGivenIsNullAndEndTimeIsNull(qq, participant)) response++;
        }
        return response;
    }

    private boolean quizQuestionIsUnanswered(QuizQuestion qq, Participant participant) {
        return resultRepo.existsByQuizQuestionAndParticipantAndAnswerGivenIsNullAndEndTimeIsNull(qq, participant);
    }
}

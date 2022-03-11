package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.*;
import be.ucll.quizmaster.quizmaster.repo.QuestionRepo;
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

    public QuizQuestionService(QuestionRepo questionRepo, ResultRepo resultRepo) {
        this.questionRepo = questionRepo;
        this.resultRepo = resultRepo;
    }


    public List<QuizQuestion> addQuestionsToQuizById(Quiz quiz, Set<Long> questionIds) {

        List<QuizQuestion> response = new ArrayList<>();

        for (Long questionId : questionIds) {

            Question question = questionRepo.findById(questionId).orElseThrow(
                    () ->  new EntityNotFoundException("no question found with id: " + questionId)
            );

            response.add(new QuizQuestion(question, quiz));
            logger.info(question.getQuestionString() + " add to " + quiz.getTitle());

        }

        return response;

    }

/*    public QuizQuestion findNextQuizQuestionWithoutEndTime(Quiz quizPlayed, Participant currentParticipation) {
        for (QuizQuestion qq : quizPlayed.getQuizQuestions()) {
            if (!quizQuestionHasResultForParticipation(qq, currentParticipation)){
                return qq;
            }
        }
        throw new RuntimeException("no question found without a answer given.");
    }

    public boolean quizQuestionHasResultForParticipation(QuizQuestion q, Participant currentParticipation) {

        return resultRepo.existsByQuizQuestionAndAndParticipantAndIsCorrectIsNullAndEndTimeIsNull(q, currentParticipation);

    }


    public boolean isFirstQuestion(Quiz quiz, Participant participant) {
        for (QuizQuestion q : quiz.getQuizQuestions()) {
            if (quizQuestionHasResultForParticipation(q, participant)){
                return false;
            }
        }
        return true;
    }*/


    public QuizQuestion findUnansweredQuizQuestion(Quiz quiz, Participant participant) {

        return null;

    }

    public boolean isFirstQuestionFromQuiz(Quiz quizPlayed, Participant participation) {

        for (QuizQuestion qq : quizPlayed.getQuizQuestions()) {
            if (resultRepo.existsByQuizQuestionAndAndParticipant(qq, participation)){
                return false;
            }
        }
        return true;

    }
}

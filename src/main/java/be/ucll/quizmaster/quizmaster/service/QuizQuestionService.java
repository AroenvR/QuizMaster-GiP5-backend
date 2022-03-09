package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.*;
import be.ucll.quizmaster.quizmaster.repo.QuestionRepo;
import be.ucll.quizmaster.quizmaster.repo.QuizQuestionRepo;
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

    public boolean quizQuestionHasResultForParticipation(QuizQuestion q, Participant currentParticipation) {

        return resultRepo.existsByQuizQuestionAndAndParticipant(q, currentParticipation);

    }
}

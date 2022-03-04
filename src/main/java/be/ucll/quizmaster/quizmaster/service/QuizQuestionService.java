package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.Question;
import be.ucll.quizmaster.quizmaster.model.Quiz;
import be.ucll.quizmaster.quizmaster.model.QuizQuestion;
import be.ucll.quizmaster.quizmaster.repo.QuestionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
public class QuizQuestionService {

    private final Logger logger = LoggerFactory.getLogger(QuizQuestionService.class);

    private final QuestionRepo questionRepo;

    public QuizQuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }


    public Set<QuizQuestion> addQuestionsToQuizById(Quiz quiz, Set<Long> questionIds) {

        Set<QuizQuestion> response = new HashSet<>();

        for (Long questionId : questionIds) {

            Question question = questionRepo.findById(questionId).orElseThrow(
                    () ->  new EntityNotFoundException("no question found with id: " + questionId)
            );

            response.add(new QuizQuestion(question, quiz));
            logger.info(question.toString() + " add to " + quiz.toString());

        }

        return response;

    }
}

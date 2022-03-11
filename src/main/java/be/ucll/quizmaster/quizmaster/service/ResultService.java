package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.QuizQuestion;
import be.ucll.quizmaster.quizmaster.model.Result;
import org.springframework.stereotype.Service;

@Service
public class ResultService {

    private final ResultRepo resultRepo;

    public ResultService(ResultRepo resultRepo) {
        this.resultRepo = resultRepo;
    }

    public Result saveResult(Result result) {

        //checkResult(result);

        return resultRepo.save(result);

    }

    public Result getResultBy(QuizQuestion quizQuestion, Participant participant) {

        return resultRepo.getByQuizQuestionAndParticipant(quizQuestion, participant).orElseThrow();

    }

    public void updateResult(Result currentResult) {
    }
}

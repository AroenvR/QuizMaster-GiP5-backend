package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.QuizQuestion;
import be.ucll.quizmaster.quizmaster.model.Result;
import liquibase.pro.packaged.P;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepo extends JpaRepository<Result, Long> {

    boolean existsByQuizQuestionAndAndParticipant(QuizQuestion quizQuestion, Participant participant);

}

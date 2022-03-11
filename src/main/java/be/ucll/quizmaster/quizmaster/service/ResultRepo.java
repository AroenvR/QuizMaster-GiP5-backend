package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.QuizQuestion;
import be.ucll.quizmaster.quizmaster.model.Result;
import liquibase.pro.packaged.P;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultRepo extends JpaRepository<Result, Long> {

    boolean existsByQuizQuestionAndParticipant(QuizQuestion quizQuestion, Participant participant);

    boolean existsByQuizQuestionAndParticipantAndAnswerGivenIsNullAndEndTimeIsNull(QuizQuestion quizQuestion, Participant participant);

    Optional<Result> getByQuizQuestionAndParticipant(QuizQuestion quizQuestion, Participant participant);

}

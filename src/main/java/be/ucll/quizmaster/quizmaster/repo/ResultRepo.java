package be.ucll.quizmaster.quizmaster.repo;

import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.QuizQuestion;
import be.ucll.quizmaster.quizmaster.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ResultRepo extends JpaRepository<Result, Long> {

    boolean existsByQuizQuestionAndParticipant(QuizQuestion quizQuestion, Participant participant);

    boolean existsByQuizQuestionAndParticipantAndAnswerGivenIsNullAndEndTimeIsNull(QuizQuestion quizQuestion, Participant participant);

    Optional<Result> getByQuizQuestionAndParticipant(QuizQuestion quizQuestion, Participant participant);

    List<Result> getAllByParticipant(Participant participant);

    @Query(value = "SELECT count(result_id) FROM quiz_master.result where participant_id = :participationId",
            nativeQuery = true)
    int questionsAsked(@Param("participationId") long participationId);

    @Query(value = "SELECT count(result_id) FROM quiz_master.result where participant_id = :participationId AND correct" +
            " = true", nativeQuery = true)
    int questionsCorrect(@Param("participationId") long participationId);

    @Query(value = "SELECT SUM(time_taken_question.TimeTakenForQuestion)\n" +
            "FROM (SELECT extract(SECOND from end_time - start_time) + (extract(MIN from end_time - start_time) * 60 ) AS TimeTakenForQuestion\n" +
            "        FROM quiz_master.result\n" +
            "        WHERE participant_id = :participationId\n" +
            "        AND end_time IS NOT NULL) time_taken_question\n" +
            "\n", nativeQuery = true)
    int getSecondsTaken(@Param("participationId") long participationId);

}

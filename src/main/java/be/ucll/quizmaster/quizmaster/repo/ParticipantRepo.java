package be.ucll.quizmaster.quizmaster.repo;

import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, Long> {

    boolean existsByQuizAndMember(Quiz quiz, Member member);

    boolean existsByMemberAndFinishedIsFalse(Member member);

    Optional<Participant> getParticipantByMemberAndFinishedIsFalse(Member member);



}

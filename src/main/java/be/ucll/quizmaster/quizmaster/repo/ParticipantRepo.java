package be.ucll.quizmaster.quizmaster.repo;

import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepo extends CrudRepository<Participant, Long> {

    public boolean existsByQuizAndMember(Quiz quiz, Member member);

}

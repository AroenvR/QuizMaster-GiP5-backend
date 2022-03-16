package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.Quiz;
import be.ucll.quizmaster.quizmaster.repo.ParticipantRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class ParticipantService {

    private final Logger logger = LoggerFactory.getLogger(ParticipantService.class);

    private final ParticipantRepo participantRepo;

    public ParticipantService(ParticipantRepo participantRepo) {
        this.participantRepo = participantRepo;
    }

    public boolean isAlreadyInThisQuiz(Member candidateToJoin, Quiz quizToJoin) {
        return participantRepo.existsByQuizAndMember(quizToJoin, candidateToJoin);
    }

    public Participant saveParticipation(Participant participation) {

        return participantRepo.save(participation);

    }

    public boolean isAlreadyInQuizAndNotFinished(Member candidateToJoin) {
        return participantRepo.existsByMemberAndFinishedIsFalse(candidateToJoin);
    }

    @Transactional
    public void setCurrentQuizFinished(Member candidateToJoin) {
        Participant currentParticipation = getCurrentParticipation(candidateToJoin).orElseThrow(
                () -> new IllegalArgumentException("participation not found")
        );
        currentParticipation.setFinished(true);
        participantRepo.save(currentParticipation);
        logger.debug(candidateToJoin.getEmailAddress() + " his/her quiz is set to finished");
    }

    public Optional<Participant> getCurrentParticipation(Member member) {
        return participantRepo.getParticipantByMemberAndFinishedIsFalse(member);
    }


    public Participant getParticipation(Quiz quiz, Member member) {

        return participantRepo.getParticipantByMemberAndQuiz(member, quiz).orElseThrow(
                () -> new EntityNotFoundException(
                        "no participation found"
        ));

    }

    public Set<Participant> getAllParticipations(Member member) {

        return participantRepo.getAllByMember(member);

    }

    public boolean participationHasValidResult(Participant participation) {
        if (participation.getResults().get(0).getEndTime() == null) {
            return false;
        }
        return true;
    }
}

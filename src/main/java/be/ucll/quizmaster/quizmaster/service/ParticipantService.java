package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.Quiz;
import be.ucll.quizmaster.quizmaster.repo.ParticipantRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public boolean isAlreadyInAQuiz(Member candidateToJoin) {
        return participantRepo.existsByMember(candidateToJoin);
    }

    @Transactional
    public void setCurrentQuizFinished(Member candidateToJoin) {
        Participant currentParticipation = getCurrentParticipation(candidateToJoin);
        currentParticipation.setFinished(true);
        participantRepo.save(currentParticipation);
        logger.debug(candidateToJoin.getEmailAddress() + " his/her quiz is set to finished");
    }

    public Participant getCurrentParticipation(Member member) {
        return participantRepo.getParticipantByMemberAndFinishedIsFalse(member).orElseThrow(() -> new RuntimeException("participation not found"));
    }



}

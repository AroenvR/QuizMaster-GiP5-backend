package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.Quiz;
import be.ucll.quizmaster.quizmaster.repo.ParticipantRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    private final Logger logger = LoggerFactory.getLogger(ParticipantService.class);

    private final ParticipantRepo participantRepo;

    public ParticipantService(ParticipantRepo participantRepo) {
        this.participantRepo = participantRepo;
    }

    public boolean isAlreadyInQuiz(Member candidateToJoin, Quiz quizToJoin) {
        return participantRepo.existsByQuizAndMember(quizToJoin, candidateToJoin);
    }

    public Participant saveParticipation(Participant participation) {

        return participantRepo.save(participation);

    }
}

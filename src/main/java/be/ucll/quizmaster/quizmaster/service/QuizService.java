package be.ucll.quizmaster.quizmaster.service;


import be.ucll.quizmaster.quizmaster.controller.dto.CodeDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuizDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.QuizDTO;
import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Participant;
import be.ucll.quizmaster.quizmaster.model.Quiz;
import be.ucll.quizmaster.quizmaster.repo.QuizRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import be.ucll.quizmaster.quizmaster.service.exceptions.TimeInThePastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class QuizService {

    private final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final QuizRepo quizRepo;

    private final LoginService loginService;
    private final QuizQuestionService quizQuestionService;
    private final MemberService memberService;
    private final ParticipantService participantService;


    public QuizService(QuizRepo quizRepo,
                       LoginService loginService,
                       QuizQuestionService quizQuestionService,
                       MemberService memberService,
                       ParticipantService participantService) {
        this.quizRepo = quizRepo;
        this.loginService = loginService;
        this.quizQuestionService = quizQuestionService;
        this.memberService = memberService;
        this.participantService = participantService;
    }

    @Transactional
    public CodeDTO saveQuiz(CreateQuizDTO dto) throws NotAuthenticatedException, TimeInThePastException {

        Member host = loginService.getLoggedInMember("Not authenticated. only members can host a quiz");
        logger.debug("\nquiz host is " + host.toString());

        checkCreateDto(dto);

        Quiz toSave = new Quiz.Builder()
                .title(dto.getTitle())
                .host(host)
                .code(createQuizCode())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        toSave.setQuizQuestions(quizQuestionService.addQuestionsToQuizById(toSave, dto.getQuestionIds(), host));


        Quiz saved = quizRepo.save(toSave);
        logger.debug("\nSAVED: " + saved.toString());
        return new CodeDTO(saved.getCode());

    }


    @Transactional
    public QuizDTO joinQuiz(String quizCode) throws NotAuthenticatedException {

        Member candidateToJoin = loginService.getLoggedInMember("Not authenticated. only members can join a quiz.");

        if (!quizRepo.existsByCode(quizCode)){
            throw new IllegalArgumentException("no quiz with code '" + quizCode + "'");
        }

        Quiz quizToJoin = getQuizByCode(quizCode);

        if (candidateToJoin.equals(quizToJoin.getHost())){
            throw new IllegalArgumentException("nice try ;) the host can not join his own quiz");
        }

        if (participantService.isAlreadyInQuizAndNotFinished(candidateToJoin)) {
            if (participantService.isAlreadyInThisQuiz(candidateToJoin, quizToJoin)) {
                throw new IllegalArgumentException("You can only participate once in a quiz");
            } else {
                participantService.setCurrentQuizFinished(candidateToJoin);
            }
        }

        Participant participation = new Participant(candidateToJoin, quizToJoin);
        Participant saved = participantService.saveParticipation(participation);
        logger.debug("SAVED: " + saved);

        QuizDTO response = new QuizDTO(saved.getQuiz().getTitle(), saved.getQuiz().getHost().getUsername());
        logger.debug("Response:" + response);
        return response;

    }


    private void checkCreateDto(CreateQuizDTO dto) throws TimeInThePastException {


        if (dto.getStartTime() == null) {
            logger.debug("no start time given");
            throw new IllegalArgumentException("start time is a required field");
        } else if (dto.getStartTime().before(new Date())) {
            logger.debug("start time is time in the past");
            throw new TimeInThePastException("start time in the past");
        } else if (dto.getEndTime() == null) {
            logger.debug("no end time given");
            throw new IllegalArgumentException("end time is a required field");
        } else if (dto.getEndTime().before(new Date())) {
            logger.debug("end time is in the past");
            throw new TimeInThePastException("end time in the past");
        } else if (dto.getEndTime().before(dto.getStartTime())) { //TODO: minimum tijd?
            logger.debug("end time before start time");
            throw new TimeInThePastException("end time can't be before start time");
        } else if (dto.getTitle() == null || dto.getTitle().equals("")) {
            logger.debug("no quiz title given.");
            throw new IllegalArgumentException("quiz title is a required field");
        }

        //TODO: title uniek?

    }


    private String createQuizCode() {
        String base = UUID.randomUUID().toString();
        String code = base.substring(0, 2);
        code += base.substring(9, 10);
        code += base.substring(14, 15);
        code += base.substring(19, 20);
        code += base.substring(24, 27);
        if (quizRepo.existsByCode(code)){
            return createQuizCode();
        }
        logger.debug("quiz code is " + code);
        return code;
    }


    public Quiz getQuizByCode(String quizCode) {
        return quizRepo.getByCode(quizCode).orElseThrow(() -> new IllegalArgumentException("not quiz with code: " + quizCode));
    }


}

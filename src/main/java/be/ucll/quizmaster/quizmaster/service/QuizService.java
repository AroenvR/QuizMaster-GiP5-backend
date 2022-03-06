package be.ucll.quizmaster.quizmaster.service;


import be.ucll.quizmaster.quizmaster.config.security.MemberPrincipal;
import be.ucll.quizmaster.quizmaster.controller.dto.CodeDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuizDTO;
import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Quiz;
import be.ucll.quizmaster.quizmaster.repo.QuizRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import be.ucll.quizmaster.quizmaster.service.exceptions.TimeInThePastException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class QuizService {

    private final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final QuizRepo quizRepo;

    private final QuestionService questionService;
    private final QuizQuestionService quizQuestionService;
    private final MemberService memberService;

    public QuizService(QuizRepo quizRepo, QuestionService questionService, QuizQuestionService quizQuestionService, MemberService memberService) {
        this.quizRepo = quizRepo;
        this.questionService = questionService;
        this.quizQuestionService = quizQuestionService;
        this.memberService = memberService;
    }


    @Transactional
    public CodeDTO saveQuiz(CreateQuizDTO dto) throws NotAuthenticatedException, TimeInThePastException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken || authentication == null) {
            throw new NotAuthenticatedException("Not authenticated. only members can create a quiz.");
        }

        checkDto(dto);

        MemberPrincipal memberPrincipal = (MemberPrincipal)authentication.getPrincipal();
        Member host = memberPrincipal.getMember();
        logger.debug("\nquiz host is " + host.toString());

        Quiz toSave = new Quiz.Builder()
                .title(dto.getTitle())
                .host(host)
                .code(createQuizCode())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        toSave.setQuizQuestions(quizQuestionService.addQuestionsToQuizById(toSave, dto.getQuestionIds()));

        Quiz saved = quizRepo.save(toSave);
        logger.debug("\nSAVED: " + saved.toString());
        return new CodeDTO(saved.getCode());

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

    private void checkDto(CreateQuizDTO dto) throws TimeInThePastException {

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


}

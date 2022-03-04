package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.config.security.MemberPrincipal;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateAnswerDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import be.ucll.quizmaster.quizmaster.model.Answer;
import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.Question;
import be.ucll.quizmaster.quizmaster.model.Topic;
import be.ucll.quizmaster.quizmaster.repo.QuestionRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionService {

    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepo questionRepo;
    private final TopicService topicService;

    public QuestionService(QuestionRepo questionRepo, TopicService topicService) {
        this.questionRepo = questionRepo;
        this.topicService = topicService;
    }

    @Transactional
    public CreateQuestionDTO saveQuestion(CreateQuestionDTO dto) throws NotAuthenticatedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken || authentication == null) {
            throw new NotAuthenticatedException("Not authenticated. only members can create a question.");
        }

        checkDto(dto);

        MemberPrincipal memberPrincipal = (MemberPrincipal) authentication.getPrincipal();
        Member creator = memberPrincipal.getMember();
        logger.debug("question creator is " + creator.toString());


        Topic topic;
        if (topicService.topicExists(dto.getTopic())) {
            topic = topicService.getTopic(dto.getTopic());
            logger.debug("topic " + dto.getTopic() + " already exists. existing topic is used");
        } else {
            topic = new Topic(dto.getTopic());
            topicService.saveTopic(topic);
            logger.debug("topic " + dto.getTopic() + " does not exist, topic is added");
        }

        Question question = new Question.Builder()
                .questionString(dto.getQuestionString())
                .description(dto.getDescription())
                .member(creator)
                .topic(topic)
                .type(dto.getType())
                .build();


        for (CreateAnswerDTO answerDto : dto.getAnswersDTOs()) {
            question.addAnswer(new Answer(answerDto.getAnswerString(), answerDto.isCorrect(), question));
        }

        questionRepo.save(question);
        logger.info("SAVED: " + question.toString());
        return dto;

    }

    private void checkDto(CreateQuestionDTO dto) {

        if (dto.getQuestionString() == null || dto.getQuestionString().equals("")) {
            throw new IllegalArgumentException("question string is a required field");
        }
        if (dto.getDescription() == null || dto.getDescription().equals("")) {
            throw new IllegalArgumentException("description is a required field");
        }
        if (dto.getTopic() == null || dto.getTopic().equals("")) {
            throw new IllegalArgumentException("topic is a required field");
        }
        if (dto.getAnswersDTOs() == null || dto.getAnswersDTOs().size() == 0) {
            throw new IllegalArgumentException("a question must have at least 1 answer");
        }

        if (dto.getType() == 0) {
            throw new IllegalArgumentException("type is a required field");
        }


        int correctAnswerCount = 0;
        for (CreateAnswerDTO answerDto : dto.getAnswersDTOs()) {
            if (answerDto.getAnswerString() == null || answerDto.getAnswerString().equals("")) {
                throw new IllegalArgumentException("answer string is a required field");
            } else if (answerDto.isCorrect()) {
                correctAnswerCount++;
            }
        }

        if (correctAnswerCount == 0) {
            throw new IllegalArgumentException("there must be 1 correct answer per question");
        }

    }
}

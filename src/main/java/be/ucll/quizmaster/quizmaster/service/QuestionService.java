package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.QuestionDTO;
import be.ucll.quizmaster.quizmaster.model.*;
import be.ucll.quizmaster.quizmaster.repo.QuestionRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepo questionRepo;
    private final TopicService topicService;
    private final LoginService loginService;
    private final QuizQuestionService quizQuestionService;
    private final ParticipantService participantService;
    private final ResultService resultService;
    private final AnswerService answerService;

    public QuestionService(QuestionRepo questionRepo,
                           TopicService topicService,
                           LoginService loginService,
                           QuizQuestionService quizQuestionService,
                           ParticipantService participantService,
                           ResultService resultService, AnswerService answerService) {
        this.questionRepo = questionRepo;
        this.topicService = topicService;
        this.loginService = loginService;
        this.quizQuestionService = quizQuestionService;
        this.participantService = participantService;
        this.resultService = resultService;
        this.answerService = answerService;
    }

    @Transactional
    public CreateQuestionDTO saveQuestion(CreateQuestionDTO dto) throws NotAuthenticatedException {

        Member creator = loginService.getLoggedInMember("Not authenticated. only members can create a question.");
        logger.debug("question creator is " + creator.toString());

        checkDto(dto);

        Topic topic;
        if (topicService.topicExists(dto.getTopic())) {
            topic = topicService.getTopic(dto.getTopic());
            logger.debug("topic " + dto.getTopic() + " already exists. existing topic is used");
        } else {
            topic = new Topic(dto.getTopic());
            topicService.saveTopic(topic);
            logger.debug("topic " + dto.getTopic() + " does not exist, topic is added");
        }

        Question toSave = new Question.Builder()
                .questionString(dto.getQuestionString())
                .description(dto.getDescription())
                .member(creator)
                .topic(topic)
                .type(dto.getType())
                .build();

        setAnswersInQuestion(toSave, dto.getAnswers());

        Question saved = questionRepo.save(toSave);
        logger.info("SAVED: " + saved.toString());
        return new CreateQuestionDTO(saved.getQuestionId(), dto);

    }

    public QuestionDTO getNextQuestion(String answerToPrevious) throws NotAuthenticatedException {

        Member loggedInMember = loginService.getLoggedInMember("only members can get a question");

        Participant currentParticipation = participantService.getCurrentParticipation(loggedInMember);
        Quiz quizPlayed = currentParticipation.getQuiz();

        if (quizPlayed.getStartTime().after(new Date())) {
            throw new IllegalArgumentException("Quiz did not start yet.");
        } else if (quizPlayed.getEndTime().before(new Date())) {
            throw new IllegalArgumentException("The quiz is closed.");
        }


        if(!quizQuestionService.isFirstQuestionFromQuiz(quizPlayed, currentParticipation)){
            logger.debug("member already got a question from this quiz.");
            saveAnswerGiven(answerToPrevious, quizPlayed, currentParticipation);
        } else {
            logger.debug("this is the first question for this member in this quiz.");
        }

        Question nextQuestion = prepareNextQuestion(quizPlayed, currentParticipation);


        return null;

    }


    @Transactional
    public void saveAnswerGiven(String answerToPrevious, Quiz quizPlayed, Participant currentParticipation) {

        QuizQuestion q = quizQuestionService.findUnansweredQuizQuestion(quizPlayed, currentParticipation);

    }

    private Question prepareNextQuestion(Quiz quizPlayed, Participant currentParticipation) {
        return null;
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
        if (dto.getAnswers() == null || dto.getAnswers().size() == 0) {
            throw new IllegalArgumentException("a question must have at least 1 answer");
        }

        if (dto.getType() == 0) {
            throw new IllegalArgumentException("type is a required field");
        } else if (dto.getType() < 0 || dto.getType() > 3) {
            throw new IllegalArgumentException("type must be 1, 2 or 3");
        }

        int correctAnswerCount = 0;
        for (String answer : dto.getAnswers()) {
            if (answer == null || answer.equals("")) {
                throw new IllegalArgumentException("answer string is a required field");
            }
        }

        switch (dto.getType()) {
            case 1: //multiple choice
                if (dto.getAnswers().size() < 2) {
                    throw new IllegalArgumentException("a multiple choice question must have at least 2 answers");
                } else if (dto.getAnswers().size() > 10) {
                    throw new IllegalArgumentException("a multiple choice question can not have more than 10 answers");
                }
                break;
            case 2:
                if (dto.getAnswers().size() != 1) {
                    throw new IllegalArgumentException("a true or false question has only one right answer");
                }
                break;
            case 3:
                if (dto.getAnswers().size() > 10) {
                    throw new IllegalArgumentException("a fill in de blank question can not have more than 10 answers");

                }
        }

        for (int i = 0; i < dto.getAnswers().size(); i++) {
            for (int j = i + 1; j < dto.getAnswers().size(); j++) {
                if (dto.getAnswers().get(i).equals(dto.getAnswers().get(j))) {
                    throw new IllegalArgumentException("every answer must be unique");
                }
            }
        }

    }

    private void setAnswersInQuestion(Question toSave, List<String> answers) {
        switch (toSave.getType()) {
            case 1: //multiple choice
                toSave.addAnswer(new Answer(answers.get(0), true, toSave));
                for (int i = 1; i < answers.size(); i++) {
                    toSave.addAnswer(new Answer(answers.get(i), false, toSave));
                }
                break;
            case 2:
                if (answers.get(0).equalsIgnoreCase("true")) {
                    //true is het juiste antwoord
                    toSave.addAnswer(new Answer(answers.get(0), true, toSave));
                    //false is niet het jusite werk
                    toSave.addAnswer(new Answer("false", false, toSave));
                } else if (answers.get(0).equalsIgnoreCase("false")) {
                    //true is niet het juiste antwoord
                    toSave.addAnswer(new Answer("true", false, toSave));
                    //false is het juiste antwoord
                    toSave.addAnswer(new Answer(answers.get(0), true, toSave));
                } else throw new IllegalArgumentException("the answer can only be true or false");
                break;
            case 3:
                for (String answer : answers) {
                    toSave.addAnswer(new Answer(answer, true, toSave));
                }
                break;
        }

    }



}


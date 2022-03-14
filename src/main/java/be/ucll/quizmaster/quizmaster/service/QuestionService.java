package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.controller.dto.AnswerDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.CreateQuestionDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.QuestionDTO;
import be.ucll.quizmaster.quizmaster.controller.dto.TopicDTO;
import be.ucll.quizmaster.quizmaster.model.*;
import be.ucll.quizmaster.quizmaster.repo.QuestionRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import be.ucll.quizmaster.quizmaster.service.exceptions.QuizFinishedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    public QuestionService(QuestionRepo questionRepo,
                           TopicService topicService,
                           LoginService loginService,
                           QuizQuestionService quizQuestionService,
                           ParticipantService participantService,
                           ResultService resultService) {
        this.questionRepo = questionRepo;
        this.topicService = topicService;
        this.loginService = loginService;
        this.quizQuestionService = quizQuestionService;
        this.participantService = participantService;
        this.resultService = resultService;
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

        setAnswersInRecievedQuestion(toSave, dto.getAnswers());

        Question saved = questionRepo.save(toSave);
        logger.info("SAVED: " + saved.toString());
        return new CreateQuestionDTO(saved.getQuestionId(), dto);

    }

    public QuestionDTO getNextQuestion(String answerToPrevious) throws NotAuthenticatedException {

        Member loggedInMember = loginService.getLoggedInMember("only members can get a question");

        Participant currentParticipation = participantService.getCurrentParticipation(loggedInMember).orElseThrow(
                () -> new RuntimeException("you need to join a quiz before you can get your first question")
        );


        Quiz quizPlayed = currentParticipation.getQuiz();

        checkQuizTime(quizPlayed);

        boolean isBreak = quizQuestionService.isBreak(quizPlayed, currentParticipation);

        if (!quizQuestionService.isFirstQuestionFromQuiz(quizPlayed, currentParticipation)
                && !quizQuestionService.isAfterBreak(quizPlayed, currentParticipation)) {

            logger.debug("member already got a question from this quiz.");
            resultService.saveAnswerGiven(answerToPrevious, quizPlayed, currentParticipation);


        } else {
            logger.debug("this is the first question of the quiz of after the break.");
        }

        if (isBreak) {
            QuestionDTO response = new QuestionDTO.Builder()
                    .isBreak(true)
                    .build();
            logger.debug("BREAK for " + currentParticipation.getMember().getUsername() + " in quiz " + quizPlayed.getTitle());
            return response;
        }

        Question q = prepareNextQuestion(quizPlayed, currentParticipation);
        logger.debug(q.toString());

        QuestionDTO response = new QuestionDTO.Builder()
                .questionString(q.getQuestionString())
                .description(q.getDescription())
                .type(q.getType())
                .topic(q.getTopic().getName())
                .answers(getAnswersForPreparedQuestion(q))
                .isBreak(false)
                .quizTitle(quizPlayed.getTitle())
                .build();
        logger.info("NEXT Question -> " + q.toString());
        return response;

    }


    private Question prepareNextQuestion(Quiz quizPlayed, Participant currentParticipation) {

        QuizQuestion nextQuizQuestion;
        try {
            nextQuizQuestion = quizQuestionService.findQuizQuestionToBeStarted(quizPlayed, currentParticipation);
        } catch (QuizFinishedException quizFinishedException) {
            participantService.setCurrentQuizFinished(currentParticipation.getMember());
            throw quizFinishedException;
        }

        Result resultForNextQuestion = new Result.Builder()
                .quizQuestion(nextQuizQuestion)
                .participant(currentParticipation)
                .startTime(new Date())
                .isCorrect(false)
                .build();

        resultService.saveResult(resultForNextQuestion);

        Question nextQuestion = nextQuizQuestion.getQuestion();
        logger.debug("next question = " + nextQuestion.toString());

        return nextQuestion;

    }

    public List<QuestionDTO> getQuestionsByTopic(String topic) throws NotAuthenticatedException {
        List<QuestionDTO> listToReturn = new ArrayList<>();

        Topic requestedTopic = topicService.getTopic(topic);

        for (Question q: questionRepo.getQuestionsByTopic(requestedTopic)) {

            Set<String> answersFromQuestion = new HashSet<>();
            for(Answer answer: q.getAnswers()) {
                String answerToAdd = answer.getAnswerString();
                answersFromQuestion.add(answerToAdd);
            }

            QuestionDTO dtoToAdd = new QuestionDTO();

            dtoToAdd.setQuestionString(q.getQuestionString());
            dtoToAdd.setAnswers(answersFromQuestion);
            dtoToAdd.setTopic(q.getTopic().getName());
            dtoToAdd.setDescription(q.getDescription());
            dtoToAdd.setType(q.getType());

            listToReturn.add(dtoToAdd);
        }

        return listToReturn;
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

    private void checkQuizTime(Quiz quizPlayed) {
        if (quizPlayed.getStartTime().after(new Date())) {
            throw new IllegalArgumentException("Quiz did not start yet.");
        } else if (quizPlayed.getEndTime().before(new Date())) {
            throw new IllegalArgumentException("The quiz is closed.");
        }
    }

    private void setAnswersInRecievedQuestion(Question toSave, List<String> answers) {
        switch (toSave.getType()) {
            case 1: //multiple choice
                toSave.addAnswer(new Answer(answers.get(0), true, toSave));
                for (int i = 1; i < answers.size(); i++) {
                    toSave.addAnswer(new Answer(answers.get(i), false, toSave));
                }
                break;
            case 2: //true false
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

    private Set<String> getAnswersForPreparedQuestion(Question q) {

        if (q.getType() == 3) {
            return new HashSet<>();
        }
        return q.getAnswers().stream().map(Answer::getAnswerString).collect(Collectors.toCollection(HashSet::new));

    }


}


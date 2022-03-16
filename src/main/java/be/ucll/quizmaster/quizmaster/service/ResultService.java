package be.ucll.quizmaster.quizmaster.service;

import be.ucll.quizmaster.quizmaster.controller.dto.FeedbackDTO;
import be.ucll.quizmaster.quizmaster.model.*;
import be.ucll.quizmaster.quizmaster.repo.ResultRepo;
import be.ucll.quizmaster.quizmaster.service.exceptions.NotAuthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final Logger logger = LoggerFactory.getLogger(ResultService.class);

    private final ResultRepo resultRepo;
    private final QuizQuestionService quizQuestionService;
    private final LoginService loginService;
    private final ParticipantService participantService;
    private final QuizService quizService;

    public ResultService(ResultRepo resultRepo, QuizQuestionService quizQuestionService, LoginService loginService, ParticipantService participantService, QuizService quizService) {
        this.resultRepo = resultRepo;
        this.quizQuestionService = quizQuestionService;
        this.loginService = loginService;
        this.participantService = participantService;
        this.quizService = quizService;
    }

    public Result saveResult(Result result) {
        //checkResult(result);
        return resultRepo.save(result);
    }

    public Result getResultBy(QuizQuestion quizQuestion, Participant participant) {

        return resultRepo.getByQuizQuestionAndParticipant(quizQuestion, participant).orElseThrow();


    }

    @Transactional
    public void saveAnswerGiven(String answerToPrevious, Quiz quizPlayed, Participant currentParticipation) {

        QuizQuestion q = quizQuestionService.findQuizQuestionToBeAnswered(quizPlayed, currentParticipation);
        logger.debug("the previous question we have send this user was " + q.getQuestion().getQuestionString() + " from quiz with code " + q.getQuiz().getCode());

        Result resultToUpdate = getResultBy(q, currentParticipation);

        resultToUpdate.setEndTime(new Date());
        if (answerToPrevious == null || answerToPrevious.strip().equals("")) {
            resultToUpdate.setAnswerGiven("NO ANSWER GIVEN");
            resultToUpdate.setIsCorrect(false);
        } else {
            resultToUpdate.setAnswerGiven(answerToPrevious);
            resultToUpdate.setIsCorrect(isCorrectAnswer(q.getQuestion(), answerToPrevious));

        }

        logger.info("UPDATED Result -> " + resultToUpdate.toString());

    }

    private boolean isCorrectAnswer(Question question, String answerGiven) {
        for (Answer a : question.getAnswers()) {

            if (a.getAnswerString().strip().equalsIgnoreCase(answerGiven.strip()) && a.isCorrect()) {
                return true;
            }
        }
        return false;
    }

    public FeedbackDTO getResultForQuiz(String code) throws NotAuthenticatedException {


        Member loggedInMember = loginService.getLoggedInMember("only members can get their result");

        if (code == null || code.strip().equals("")) {
            throw new IllegalArgumentException("no quiz code given.");
        }

        Quiz quizPlayed = quizService.getQuizByCode(code);

        if (!participantService.isAlreadyInThisQuiz(loggedInMember, quizPlayed)) {
            throw new IllegalArgumentException("you did not participate in this quiz.");
        }

        Participant participation = participantService.getParticipation(quizPlayed, loggedInMember);

        if (!participation.isFinished() || participation.getResults().size() == 0) {
            throw new IllegalArgumentException("you need to finish a quiz before you can get your result.");
        }


        FeedbackDTO response = new FeedbackDTO.Builder()
                .totalAnswers(resultRepo.questionsAsked(participation.getParticipantionId()))
                .answersCorrect(getScoreForParticipation(participation))
                .minutesTaken(getMinutesTaken(participation))
                .secondsTaken(getSecondsTakenModuloMinutes(participation))
                .topTen(getTopTenForQuiz(quizPlayed))
                .place(getPlaceInQuiz(loggedInMember.getUsername(), quizPlayed))
                .build();

        logger.debug(response.toString());

        return response;

    }

    private int getPlaceInQuiz(String username, Quiz quiz) {
        int place = 1;
        for (Map.Entry<String, Integer> entry : getLeaderBoardForQuiz(quiz).entrySet()) {
            if (entry.getKey().equals(username)) {
                return place;
            }
            place++;
        }
        logger.warn("could not find member in leaderbord");
        return 0;
    }

    private List<String> getTopTenForQuiz(Quiz quizPlayed) {

        List<String> response = new ArrayList<>();

        int count = 1;

        for (Map.Entry<String, Integer> entry : getLeaderBoardForQuiz(quizPlayed).entrySet()) {
            if (count <= 10) {
                response.add(entry.getKey());
                logger.debug(entry.getKey() + " staat op plaats " + count);
            }
            count++;
        }

        return response;
    }


    private LinkedHashMap<String, Integer> getLeaderBoardForQuiz(Quiz quiz) {

        Map<String, Integer> scorePerParticipation = new HashMap<>();
        for (Participant p : quiz.getParticipants()) {
            if (p.isFinished()) {
                int score = getScoreForParticipation(p);
                scorePerParticipation.put(p.getMember().getUsername(), score);
                logger.debug(p.getMember().getUsername() + " heeft " + score + " vragen juist.");
            }
        }
        return scorePerParticipation.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private int getScoreForParticipation(Participant participation) {
        return resultRepo.questionsCorrect(participation.getParticipantionId());
    }

    private int getSecondsTakenModuloMinutes(Participant participation) {
        return resultRepo.getSecondsTaken(participation.getParticipantionId()) % 60;
    }

    private int getMinutesTaken(Participant participation) {
        int secondsTaken = resultRepo.getSecondsTaken(participation.getParticipantionId());
        return (secondsTaken - secondsTaken % 60) / 60;
    }

}

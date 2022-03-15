package be.ucll.quizmaster.quizmaster.service.exceptions;

public class QuizFinishedException extends RuntimeException {

    public QuizFinishedException(String message) {
        super(message);
    }
}

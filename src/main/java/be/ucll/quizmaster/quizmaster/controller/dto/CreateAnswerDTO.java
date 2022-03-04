package be.ucll.quizmaster.quizmaster.controller.dto;

public class CreateAnswerDTO {

    private String answerString;

    private boolean correct;

    public CreateAnswerDTO() {
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "CreateAnswerDTO{" +
                "answerString='" + answerString + '\'' +
                ", correct=" + correct +
                '}';
    }
}

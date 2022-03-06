package be.ucll.quizmaster.quizmaster.controller.dto;

public class CreateAnswerDTO {

    private String answerString;

    private boolean correct;

    public CreateAnswerDTO() {
    }

    private CreateAnswerDTO(Builder builder) {
        setAnswerString(builder.answerString);
        setCorrect(builder.correct);
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

    public static final class Builder {
        private String answerString;
        private boolean correct;

        public Builder() {
        }

        public Builder answerString(String val) {
            answerString = val;
            return this;
        }

        public Builder correct(boolean val) {
            correct = val;
            return this;
        }

        public CreateAnswerDTO build() {
            return new CreateAnswerDTO(this);
        }
    }
}

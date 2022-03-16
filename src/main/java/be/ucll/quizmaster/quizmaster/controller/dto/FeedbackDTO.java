package be.ucll.quizmaster.quizmaster.controller.dto;

import java.util.List;

public class FeedbackDTO {

    private int answersCorrect;

    private int totalAnswers;

    private int minutesTaken;

    private int secondsTaken;

    private int place;

    private List<String> topTen;

    public FeedbackDTO() {
    }

    private FeedbackDTO(Builder builder) {
        setAnswersCorrect(builder.answersCorrect);
        setTotalAnswers(builder.totalAnswers);
        setMinutesTaken(builder.minutesTaken);
        setSecondsTaken(builder.secondsTaken);
        setPlace(builder.place);
        setTopTen(builder.topTen);
    }


    public int getAnswersCorrect() {
        return answersCorrect;
    }

    public void setAnswersCorrect(int answersCorrect) {
        this.answersCorrect = answersCorrect;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    public int getMinutesTaken() {
        return minutesTaken;
    }

    public void setMinutesTaken(int minutesTaken) {
        this.minutesTaken = minutesTaken;
    }

    public int getSecondsTaken() {
        return secondsTaken;
    }

    public void setSecondsTaken(int secondsTaken) {
        this.secondsTaken = secondsTaken;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public List<String> getTopTen() {
        return topTen;
    }

    public void setTopTen(List<String> topTen) {
        this.topTen = topTen;
    }

    public static final class Builder {
        private int answersCorrect;
        private int totalAnswers;
        private int minutesTaken;
        private int secondsTaken;
        private int place;
        private List<String> topTen;

        public Builder() {
        }

        public Builder answersCorrect(int val) {
            answersCorrect = val;
            return this;
        }

        public Builder totalAnswers(int val) {
            totalAnswers = val;
            return this;
        }

        public Builder minutesTaken(int val) {
            minutesTaken = val;
            return this;
        }

        public Builder secondsTaken(int val) {
            secondsTaken = val;
            return this;
        }

        public Builder place(int val) {
            place = val;
            return this;
        }

        public Builder topTen(List<String> val) {
            topTen = val;
            return this;
        }

        public FeedbackDTO build() {
            return new FeedbackDTO(this);
        }
    }


    @Override
    public String toString() {
        return "FeedbackDTO{" +
                "answersCorrect=" + answersCorrect +
                ", totalAnswers=" + totalAnswers +
                ", minutesTaken=" + minutesTaken +
                ", secondsTaken=" + secondsTaken +
                ", place=" + place +
                ", topTen=" + topTen +
                '}';
    }
}

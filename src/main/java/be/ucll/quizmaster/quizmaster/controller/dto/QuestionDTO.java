package be.ucll.quizmaster.quizmaster.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class QuestionDTO {

    private String quizTitle;

    private String questionString;

    private int type;

    private Set<String> answers;

    @JsonProperty("break")
    private boolean isBreak;

    private String topic;

    private String description;

    public QuestionDTO() {
    }

    private QuestionDTO(Builder builder) {
        setQuizTitle(builder.quizTitle);
        setQuestionString(builder.questionString);
        setType(builder.type);
        setAnswers(builder.answers);
        setBreak(builder.isBreak);
        setTopic(builder.topic);
        setDescription(builder.description);
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
    }

    public boolean isBreak() {
        return isBreak;
    }

    public void setBreak(boolean aBreak) {
        isBreak = aBreak;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final class Builder {
        private String quizTitle;
        private String questionString;
        private int type;
        private Set<String> answers;
        private boolean isBreak;
        private String topic;
        private String description;

        public Builder() {
        }

        public Builder quizTitle(String val) {
            quizTitle = val;
            return this;
        }

        public Builder questionString(String val) {
            questionString = val;
            return this;
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public Builder answers(Set<String> val) {
            answers = val;
            return this;
        }

        public Builder isBreak(boolean val) {
            isBreak = val;
            return this;
        }

        public Builder topic(String val) {
            topic = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public QuestionDTO build() {
            return new QuestionDTO(this);
        }
    }
}

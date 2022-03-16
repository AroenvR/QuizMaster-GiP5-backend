package be.ucll.quizmaster.quizmaster.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ChooseQuestionDTO {

    private long questionId;

    private String questionString;

    private int type;

    private Set<String> answers;

    private String topic;

    private String description;


    @SuppressWarnings("unused")
    public ChooseQuestionDTO() {
    }

    private ChooseQuestionDTO(Builder builder) {
        setQuestionId(builder.questionId);
        setQuestionString(builder.questionString);
        setType(builder.type);
        setAnswers(builder.answers);
        setTopic(builder.topic);
        setDescription(builder.description);
    }


    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
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
        private long questionId;
        private String questionString;
        private int type;
        private Set<String> answers;
        private String topic;
        private String description;

        public Builder() {
        }

        public Builder questionId(long val) {
            questionId = val;
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

        public Builder topic(String val) {
            topic = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public ChooseQuestionDTO build() {
            return new ChooseQuestionDTO(this);
        }
    }
}

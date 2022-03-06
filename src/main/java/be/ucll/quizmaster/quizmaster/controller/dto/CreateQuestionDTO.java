package be.ucll.quizmaster.quizmaster.controller.dto;

import be.ucll.quizmaster.quizmaster.model.Answer;
import be.ucll.quizmaster.quizmaster.model.Member;
import be.ucll.quizmaster.quizmaster.model.QuizQuestion;
import be.ucll.quizmaster.quizmaster.model.Topic;

import javax.persistence.*;
import java.util.Set;

public class CreateQuestionDTO {

    private String questionString;

    private int type;

    private String description;

    private String topic;

    private Set<CreateAnswerDTO> answersDTOs;

    public CreateQuestionDTO() {
    }

    private CreateQuestionDTO(Builder builder) {
        setQuestionString(builder.questionString);
        setType(builder.type);
        setDescription(builder.description);
        setTopic(builder.topic);
        setAnswersDTOs(builder.answersDTOs);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Set<CreateAnswerDTO> getAnswersDTOs() {
        return answersDTOs;
    }

    public void setAnswersDTOs(Set<CreateAnswerDTO> answersDTOs) {
        this.answersDTOs = answersDTOs;
    }

    @Override
    public String toString() {
        return "CreateQuestionDTO{" +
                "questionString='" + questionString + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", topic='" + topic + '\'' +
                ", answersDTOs=" + answersDTOs +
                '}';
    }

    public static final class Builder {
        private String questionString;
        private int type;
        private String description;
        private String topic;
        private Set<CreateAnswerDTO> answersDTOs;

        public Builder() {
        }

        public Builder questionString(String val) {
            questionString = val;
            return this;
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder topic(String val) {
            topic = val;
            return this;
        }

        public Builder answersDTOs(Set<CreateAnswerDTO> val) {
            answersDTOs = val;
            return this;
        }

        public CreateQuestionDTO build() {
            return new CreateQuestionDTO(this);
        }
    }
}

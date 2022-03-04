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
}

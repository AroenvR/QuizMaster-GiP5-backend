package be.ucll.quizmaster.quizmaster.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.Date;
import java.util.Set;

public class CreateQuizDTO {


    @JsonProperty("quizTitle")
    private String quizTitle;

    //@JsonProperty("startTime")
    private Date startTime;

    //@JsonProperty("startTime")
    private Date endTime;

    private Set<Long> questionIds;


    public CreateQuizDTO() {
    }


    public String getTitle() {
        return quizTitle;
    }

    public void setTitle(String title) {
        this.quizTitle = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Set<Long> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(Set<Long> questionIds) {
        this.questionIds = questionIds;
    }


    @Override
    public String toString() {
        return "CreateQuizDTO{" +
                "quizTitle='" + quizTitle + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", questionIds=" + questionIds +
                '}';
    }
}

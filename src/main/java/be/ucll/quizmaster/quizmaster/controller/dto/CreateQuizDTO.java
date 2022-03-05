package be.ucll.quizmaster.quizmaster.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.util.Date;
import java.util.Set;

public class CreateQuizDTO {

    private long quizId;

    //@JsonProperty("title")
    private String title;

    //@JsonProperty("startTime")
    private Date startTime;

    //@JsonProperty("startTime")
    private Date endTime;

    private Set<Long> questionIds;

    public CreateQuizDTO() {
    }

    public CreateQuizDTO(long quizId, CreateQuizDTO dto) {
        this.quizId = quizId;
        this.title = dto.getTitle();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.questionIds = dto.getQuestionIds();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public long getQuizId() {
        return quizId;
    }

    public void setQuizId(long quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "CreateQuizDTO{" +
                "title='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", questionIds=" + questionIds +
                '}';
    }
}

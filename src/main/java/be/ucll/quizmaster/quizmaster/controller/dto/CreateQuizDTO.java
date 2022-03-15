package be.ucll.quizmaster.quizmaster.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class CreateQuizDTO {


    @JsonProperty("quizTitle")
    private String quizTitle;

    //@JsonFormat(pattern="yyyy-MM-ddTHH:mm:ss", locale = "nl_BE")
    private LocalDateTime startTime;

    //@JsonFormat(pattern="yyyy-MM-ddTHH:mm:ss", locale = "nl_BE")
    private LocalDateTime endTime;

    private Set<Long> questionIds;


    public CreateQuizDTO() {
    }

    private CreateQuizDTO(Builder builder) {
        setQuizTitle(builder.quizTitle);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        setQuestionIds(builder.questionIds);
    }


    public String getTitle() {
        return quizTitle;
    }

    public void setTitle(String title) {
        this.quizTitle = title;
    }


    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public Date getStartTime() {
        return Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setEndTime(LocalDateTime endTime) {
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

    public static final class Builder {
        private String quizTitle;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private Set<Long> questionIds;

        public Builder() {
        }

        public Builder quizTitle(String val) {
            quizTitle = val;
            return this;
        }

        public Builder startTime(LocalDateTime val) {
            startTime = val;
            return this;
        }

        public Builder endTime(LocalDateTime val) {
            endTime = val;
            return this;
        }

        public Builder questionIds(Set<Long> val) {
            questionIds = val;
            return this;
        }

        public CreateQuizDTO build() {
            return new CreateQuizDTO(this);
        }
    }
}

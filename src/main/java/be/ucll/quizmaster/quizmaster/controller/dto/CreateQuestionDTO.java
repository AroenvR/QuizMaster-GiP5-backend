package be.ucll.quizmaster.quizmaster.controller.dto;

import java.util.List;
import java.util.Set;

public class CreateQuestionDTO {

    private long questionId;

    private String questionString;

    private int type;

    private String description;

    private String topic;

    private List<String> answers;

    public CreateQuestionDTO() {
    }

    public CreateQuestionDTO(long questionId, CreateQuestionDTO dto) {
        this.questionId = questionId;
        this.type = dto.type;
        this.description = dto.description;
        this.questionString = dto.questionString;
        this.answers = dto.answers;
        this.topic = dto.topic;
    }


    private CreateQuestionDTO(Builder builder) {
        questionId = builder.questionId;
        setQuestionString(builder.questionString);
        setType(builder.type);
        setDescription(builder.description);
        setTopic(builder.topic);
        setAnswersDTOs(builder.answers);
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

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswersDTOs(List<String> answersDTOs) {
        this.answers = answersDTOs;
    }


    @Override
    public String toString() {
        return "CreateQuestionDTO{" +
                "questionString='" + questionString + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", topic='" + topic + '\'' +
                ", answers=" + answers +
                '}';
    }


    public static final class Builder {
        private long questionId;
        private String questionString;
        private int type;
        private String description;
        private String topic;
        private List<String > answers;

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

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder topic(String val) {
            topic = val;
            return this;
        }

        public Builder answersDTOs(List<String> val) {
            answers = val;
            return this;
        }

        public CreateQuestionDTO build() {
            return new CreateQuestionDTO(this);
        }

    }
}

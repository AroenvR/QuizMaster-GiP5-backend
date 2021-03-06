package be.ucll.quizmaster.quizmaster.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "question", schema = "quiz_master")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private long questionId;

    @Column(name = "question_string")
    private String questionString;

//  Type:
//  1/ Multiple choice
//  2/ True/false
//  3/ Fill in the blank
    @Column(name = "type")
    private int type;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<Answer> answers;

    @OneToMany(mappedBy = "question")
    private Set<QuizQuestion> quizQuestions;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    //Constructors
    private Question(Builder builder) {
        questionString = builder.questionString;
        type = builder.type;
        description = builder.description;
        answers = new HashSet<>();
        topic = builder.topic;
        member = builder.member;
    }


    public Question() {

    }

    public static final class Builder {
        private String questionString;
        private int type;
        private String description;
        private Topic topic;
        private Member member;

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

        public Builder topic(Topic val) {
            topic = val;
            return this;
        }

        public Builder member(Member val) {
            member = val;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public Set<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(Set<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "\nQuestion{" +
                "questionId=" + questionId +
                ", questionString='" + questionString + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", answers=" + answers +
                ", quizQuestions=" + quizQuestions +
                ", topic=" + topic +
                ", member=" + member.getEmailAddress() +
                "}\n";
    }

    //TODO: add as extra later
    //@Column(name = "requested")
    //private boolean isRequested;





}

package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
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

    @Column(name = "type")
    private int type;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "question")
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
        answers = builder.answers;
        topic = builder.topic;
        member = builder.member;
    }

    public Question() {

    }

    public static final class Builder {
        private String questionString;
        private int type;
        private String description;
        private Set<Answer> answers;
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

        public Builder answers(Set<Answer> val) {
            answers = val;
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

    //TODO: add as extra later
    //@Column(name = "requested")
    //private boolean isRequested;


    //Constructors



}

package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;

@Entity
@Table(name = "topic", schema = "quiz_master")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private long topic_id;

    @Column(name = "name")
    private String name;

    public Topic() {
    }

    private Topic(Builder builder) {
        setTopic_id(builder.topic_id);
        setName(builder.name);
    }

    public long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(long topic_id) {
        this.topic_id = topic_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static final class Builder {
        private long topic_id;
        private String name;

        public Builder() {
        }

        public Builder topic_id(long val) {
            topic_id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Topic build() {
            return new Topic(this);
        }
    }
}

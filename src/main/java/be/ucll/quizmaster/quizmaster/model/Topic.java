package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "topic", schema = "quiz_master")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private long topicId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "topic")
    private Set<Question> questions;

    //constructors
    public Topic() {}

    public Topic(String name) {
        this.name = name;
    }


    public long getTopicId() {
        return topicId;
    }

    private void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topic)) return false;

        Topic topic = (Topic) o;

        if (getTopicId() != topic.getTopicId()) return false;
        return getName().equals(topic.getName());
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", name='" + name + '\'' +
                '}';
    }
}

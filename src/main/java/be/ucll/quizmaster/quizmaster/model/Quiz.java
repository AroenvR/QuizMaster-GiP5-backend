package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "quiz", schema = "quiz_master")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private long quizId;

    @Column(name = "title")
    private String title;

    @Column(name = "code")
    private String code;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.PERSIST)
    private List<QuizQuestion> quizQuestions;

    @ManyToOne()
    @JoinColumn(name = "host_id")
    private Member host;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private Set<Participant> participants;

    //TODO: mogelijke uitbreiding met custom intervals
    //private int interval;

    //constructors
    public Quiz() {}

    public Quiz(Member host, String title) {
        this.host = host;
        this.title = title;
    }

    private Quiz(Builder builder) {
        setQuizId(builder.quizId);
        setTitle(builder.title);
        setCode(builder.code);
        setStartTime(builder.startTime);
        setEndTime(builder.endTime);
        quizQuestions = builder.quizQuestions;
        setHost(builder.host);
        participants = builder.participants;
    }


    //getters and setters
    public Member getHost() {
        return host;
    }

    public void setHost(Member host) {
        this.host = host;
    }

    public long getQuizId() {
        return quizId;
    }

    private void setQuizId(long quizId) {
        this.quizId = quizId;
    }


    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public Date getStartTime() {
        return startTime;
    }

    private void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    private void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;

        Quiz quiz = (Quiz) o;

        if (getQuizId() != quiz.getQuizId()) return false;
        if (getHost() != null ? !getHost().equals(quiz.getHost()) : quiz.getHost() != null) return false;
        if (!getTitle().equals(quiz.getTitle())) return false;
        if (!getCode().equals(quiz.getCode())) return false;
        if (getStartTime() != null ? !getStartTime().equals(quiz.getStartTime()) : quiz.getStartTime() != null)
            return false;
        return getEndTime() != null ? getEndTime().equals(quiz.getEndTime()) : quiz.getEndTime() == null;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", host=" + host.getUsername() +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public static final class Builder {
        private long quizId;
        private String title;
        private String code;
        private Date startTime;
        private Date endTime;
        private List<QuizQuestion> quizQuestions;
        private Member host;
        private Set<Participant> participants;

        public Builder() {
        }

        public Builder quizId(long val) {
            quizId = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder code(String val) {
            code = val;
            return this;
        }

        public Builder startTime(Date val) {
            startTime = val;
            return this;
        }

        public Builder endTime(Date val) {
            endTime = val;
            return this;
        }

        public Builder quizQuestions(List<QuizQuestion> val) {
            quizQuestions = val;
            return this;
        }

        public Builder host(Member val) {
            host = val;
            return this;
        }

        public Builder participants(Set<Participant> val) {
            participants = val;
            return this;
        }

        public Quiz build() {
            return new Quiz(this);
        }
    }
}

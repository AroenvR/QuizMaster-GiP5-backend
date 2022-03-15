package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "participant", schema = "quiz_master")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private long participantionId;

    @Column(name = "finished")
    private boolean finished;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @ManyToOne()
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "participant", fetch = FetchType.LAZY)
    private List<Result> results = new ArrayList<>();

    public Participant() {
    }


    public Participant(Member member, Quiz quiz) {
        this.member = member;
        this.quiz = quiz;
        this.finished = false;
    }

    private Participant(Builder builder) {
        setParticipantionId(builder.participantionId);
        setMember(builder.member);
        setQuiz(builder.quiz);
        setFinished(builder.finished);
        results = builder.results;
    }

    public long getParticipantionId() {
        return participantionId;
    }

    public void setParticipantionId(long participantionId) {
        this.participantionId = participantionId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public List<Result> getResults() {
        return results;
    }

    public void addResult(Result result) {
        this.results.add(result);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participant)) return false;

        Participant that = (Participant) o;

        if (getParticipantionId() != that.getParticipantionId()) return false;
        if (!getMember().equals(that.getMember())) return false;
        if (!getQuiz().equals(that.getQuiz())) return false;
        return getResults().equals(that.getResults());
    }

    @Override
    public String toString() {
        return "Participant{" +
                "participantionId=" + participantionId +
                ", finished=" + finished +
                ", member=" + member.getEmailAddress() +
                ", quiz=" + quiz.getCode() +
                ", results size =" + results +
                '}';
    }

    public static final class Builder {
        private long participantionId;
        private Member member;
        private Quiz quiz;
        private List<Result> results;
        private boolean finished;

        public Builder() {
        }

        public Builder participantionId(long val) {
            participantionId = val;
            return this;
        }

        public Builder member(Member val) {
            member = val;
            return this;
        }

        public Builder quiz(Quiz val) {
            quiz = val;
            return this;
        }

        public Builder results(List<Result> val) {
            results = val;
            return this;
        }

        public Builder finished(boolean val) {
            finished = val;
            return this;
        }

        public Participant build() {
            return new Participant(this);
        }
    }
}

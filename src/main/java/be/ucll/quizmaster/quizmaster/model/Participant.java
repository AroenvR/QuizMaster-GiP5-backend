package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "participant", schema = "quiz_master")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private long participantionId;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    @ManyToOne()
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "participant")
    private Set<Result> results;

    public Participant() {
    }


    public Participant(Member member, Quiz quiz) {
        this.member = member;
        this.quiz = quiz;
    }

    private Participant(Builder builder) {
        setParticipantionId(builder.participantionId);
        setMember(builder.member);
        setQuiz(builder.quiz);
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

    public Set<Result> getResults() {
        return results;
    }

    public void addResult(Result result) {
        this.results.add(result);
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
                ", member=" + member +
                ", quiz=" + quiz +
                ", results=" + results +
                '}';
    }

    public static final class Builder {
        private long participantionId;
        private Member member;
        private Quiz quiz;
        private Set<Result> results;

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

        public Builder results(Set<Result> val) {
            results = val;
            return this;
        }

        public Participant build() {
            return new Participant(this);
        }
    }
}

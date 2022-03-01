package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "result", schema = "quiz_master")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private long resultId;

    @ManyToOne()
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;

    @ManyToOne()
    @JoinColumn(name = "participant_id")
    private Participant participant;

    //TODO refactor with better name??
    @Column(name = "answer_string")
    private String answerGiven;

    @Column(name = "correct")
    private String isCorrect;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    //constructors
    public Result() {
    }

    public Result(QuizQuestion quizQuestion, Participant participant, String answerGiven, String isCorrect) {
        this.quizQuestion = quizQuestion;
        this.participant = participant;
        this.answerGiven = answerGiven;
        this.isCorrect = isCorrect;
    }

    public long getResultId() {
        return resultId;
    }

    private void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public QuizQuestion getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(QuizQuestion quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public String getAnswerGiven() {
        return answerGiven;
    }

    private void setAnswerGiven(String answerGiven) {
        this.answerGiven = answerGiven;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    private void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;

        Result result = (Result) o;

        if (getResultId() != result.getResultId()) return false;
        if (!getQuizQuestion().equals(result.getQuizQuestion())) return false;
        if (!getParticipant().equals(result.getParticipant())) return false;
        if (!getAnswerGiven().equals(result.getAnswerGiven())) return false;
        if (!getIsCorrect().equals(result.getIsCorrect())) return false;
        if (!getStartTime().equals(result.getStartTime())) return false;
        return getEndTime().equals(result.getEndTime());
    }

    @Override
    public String toString() {
        return "Result{" +
                "resultId=" + resultId +
                ", quizQuestion=" + quizQuestion +
                ", participant=" + participant +
                ", answerGiven='" + answerGiven + '\'' +
                ", isCorrect='" + isCorrect + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}

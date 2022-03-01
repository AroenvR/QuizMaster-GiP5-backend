package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "answer", schema = "quiz_master")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private long answerId;

    @Column(name = "correct")
    private boolean isCorrect;

    @Column(name = "answer_string")
    private String answerString;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    //constructors
    public Answer() {
    }

    public Answer(Question question, boolean isCorrect, String answerString) {
        this.question = question;
        this.isCorrect = isCorrect;
        this.answerString = answerString;
    }

    //Getters & Setters
    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;

        Answer answer = (Answer) o;

        if (getAnswerId() != answer.getAnswerId()) return false;
        if (isCorrect() != answer.isCorrect()) return false;
        if (!getQuestion().equals(answer.getQuestion())) return false;
        return getAnswerString().equals(answer.getAnswerString());
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", question=" + question +
                ", isCorrect=" + isCorrect +
                ", answerString='" + answerString + '\'' +
                '}';
    }

}
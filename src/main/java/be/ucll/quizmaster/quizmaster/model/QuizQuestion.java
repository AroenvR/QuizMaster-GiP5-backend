package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "quiz_question", schema = "quiz_master")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_question_id")
    private long quizQuestionId;

    @ManyToOne()
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne()
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "quizQuestion")
    private List<Result> results = new ArrayList<>();

    public QuizQuestion(Question question, Quiz quiz) {
        this.question = question;
        this.quiz = quiz;
    }

    public QuizQuestion() {
    }

    public long getQuizQuestionId() {
        return quizQuestionId;
    }

    public void setQuizQuestionId(long quizQuestionId) {
        this.quizQuestionId = quizQuestionId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void addResult(Result result) {
        this.results.add(result);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuizQuestion)) return false;

        QuizQuestion that = (QuizQuestion) o;

        if (quizQuestionId != that.quizQuestionId) return false;
        if (!question.equals(that.question)) return false;
        return quiz.equals(that.quiz);
    }

    @Override
    public String toString() {
        return "QuizQuestion{" +
                "quizQuestionId=" + quizQuestionId +
                ", question ID=" + question.getQuestionId() +
                ", quiz ID=" + quiz.getQuizId() +
                '}';
    }
}

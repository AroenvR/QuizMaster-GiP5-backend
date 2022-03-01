package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;


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

    public QuizQuestion(Question question, Quiz quiz) {
        this.question = question;
        this.quiz = quiz;
    }

    public QuizQuestion() {
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
                ", question=" + question +
                ", quiz=" + quiz +
                '}';
    }
}

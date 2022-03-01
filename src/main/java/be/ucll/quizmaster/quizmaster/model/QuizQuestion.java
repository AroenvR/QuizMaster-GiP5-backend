package be.ucll.quizmaster.quizmaster.model;


import javax.persistence.*;
import java.util.Set;

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

}

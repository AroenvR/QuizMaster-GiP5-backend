package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")

@Entity
@Table(name = "question", schema = "quiz_master")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private long questionId;

    @Column(name = "question_string")
    private String questionString;

    @Column(name = "type")
    private int type;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    @OneToMany(mappedBy = "question")
    private Set<QuizQuestion> quizQuestions;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;





    //TODO: add as extra later
    //@Column(name = "requested")
    //private boolean isRequested;



}

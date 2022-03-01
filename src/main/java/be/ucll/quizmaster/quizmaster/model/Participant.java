package be.ucll.quizmaster.quizmaster.model;

import javax.persistence.*;
import java.util.Set;

@SuppressWarnings("JpaDataSourceORMInspection")

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




}

package be.ucll.quizmaster.quizmaster.repo;

import be.ucll.quizmaster.quizmaster.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizQuestionRepo extends JpaRepository<QuizQuestion, Long> {


}

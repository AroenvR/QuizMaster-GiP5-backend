package be.ucll.quizmaster.quizmaster.repo;

import be.ucll.quizmaster.quizmaster.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepo extends CrudRepository<Question, Long> {

}

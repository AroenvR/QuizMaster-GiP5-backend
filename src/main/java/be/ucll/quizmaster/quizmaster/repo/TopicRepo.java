package be.ucll.quizmaster.quizmaster.repo;

import be.ucll.quizmaster.quizmaster.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Long> {

    boolean existsByName(String name);

    Topic getByName(String topic);
}

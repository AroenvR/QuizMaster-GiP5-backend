package be.ucll.quizmaster.quizmaster.repo;

import be.ucll.quizmaster.quizmaster.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MemberRepo extends JpaRepository<Member, UUID> {

    Optional<Member> getByEmailAddress(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAddress(String email);

}

package sk.backend.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.backend.server.domain.User;

@Repository
public interface UserJpaRepo extends JpaRepository<User,Long> {
}

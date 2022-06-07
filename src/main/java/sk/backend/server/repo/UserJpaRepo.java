package sk.backend.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sk.backend.server.domain.User;

@Repository
public interface UserJpaRepo extends JpaRepository<User,Long> {
    @Transactional
    @Modifying
    @Query("update User u set u.firstName = ?1, u.lastName = ?2 ,u.email = ?3 where u.id = ?4")
    void updateUser(@NonNull String firstName, @NonNull String lastName ,@NonNull  String email, Long id);
}

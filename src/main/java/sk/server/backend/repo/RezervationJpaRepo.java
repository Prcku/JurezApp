package sk.server.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;

import java.util.List;
import java.util.Optional;

public interface RezervationJpaRepo extends JpaRepository<Rezervation, Long> {

    @Transactional
    @Modifying
    @Query("update Rezervation r set r.user = ?1 where r.id = ?2")
    void rezerveTerm(User user, Long id);

    Optional<Rezervation> findFirstByCurrentTimeEqualsAndStatusIsTrueAndUser_IdEquals(String currentTime, Long id);

    Optional<Rezervation> findFirstByCurrentTimeEqualsAndStatusIsTrueAndUserIsNull(String currentTime);

    Integer countByCurrentTimeEqualsAndUserIsNotNullAndStatusIsTrue(String currentTime);

    List<Rezervation> findByCurrentTimeEquals(String current_time);

    List<Rezervation> findByUserIsNullAndStatusIsTrue();

    List<Rezervation> findByUser_IdEquals(Long id);


}

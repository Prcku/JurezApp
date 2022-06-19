package sk.server.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RezervationJpaRepo extends JpaRepository<Rezervation, Long> {

    @Transactional
    @Modifying
    @Query("update Rezervation r set r.user = ?1 where r.id = ?2")
    void rezerveTerm(User user, Long id);

    Optional<Rezervation> findFirstByCurrentTimeEqualsAndStatusTrueAndUserIsNullOrderByCurrentTimeAsc(Date currentTime);

    Optional<Rezervation> findFirstByCurrentTimeEqualsAndStatusIsTrueAndUser_IdEquals(Date currentTime, Long id);

    @Transactional
    @Modifying
    @Query("update Rezervation r set r.status = false where r.id = ?1")
    void updateStatusFalse(Long id);

//    Optional<Rezervation> findFirstByCurrentTimeEqualsAndStatusIsTrueAndUserIsNull(Date currentTime);

    Integer countByCurrentTimeEqualsAndUserIsNotNullAndStatusIsTrue(Date currentTime);

    List<Rezervation> findByCurrentTimeEquals(Date current_time);

    List<Rezervation> findByUserIsNullAndStatusIsTrue();

    @Transactional
    @Modifying
    @Query( value = "select" +
            " new sk.server.backend.domain.Rezervation(count(r),r.currentTime) " +
            " from Rezervation r where r.status = true and r.user is null GROUP BY r.currentTime order by r.currentTime")
    List<Rezervation> freeRezervation();

    List<Rezervation> findByUser_IdEquals(Long id);


}

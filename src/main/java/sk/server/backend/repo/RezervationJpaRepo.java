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

    long countByCurrentTimeEquals(Date currentTime);

    @Query("select (count(r) > 0) from Rezervation r where r.currentTime = ?1 and r.user.id = ?2")
    boolean onlyOneRezervation(Date currentTime, Long id);

    @Transactional
    @Modifying
    @Query("update Rezervation r set r.user = ?1 where r.id = ?2")

    long deleteByCurrentTimeEqualsAndUserEquals(Date currentTime, Optional<User> user);

    @Transactional
    @Modifying
    @Query("update Rezervation r set r.status = false where r.id = ?1")
    void updateStatusFalse(Long id);


    @Transactional
    @Modifying
    @Query( value = "select" +
            " new sk.server.backend.domain.Rezervation(count(r),r.currentTime) " +
            " from Rezervation r where r.status = true and r.user is null GROUP BY r.currentTime order by r.currentTime")
    List<Rezervation> freeRezervation();



}

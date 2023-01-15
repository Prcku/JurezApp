package sk.server.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sk.server.backend.domain.Rezervation;
import java.util.Date;
import java.util.List;

public interface RezervationJpaRepo extends JpaRepository<Rezervation,Long> {

    Integer countByCurrentTimeEquals(Date currentTime);

    @Query("select (count(r) > 0) from Rezervation r where r.currentTime = ?1 and r.user.id = ?2")
    boolean onlyOneRezervation(Date currentTime, Long id);

    void deleteByCurrentTimeEqualsAndUser_IdEquals(Date currentTime, Long id);

    @Transactional
    @Modifying
    @Query("update Rezervation r set r.status = false where r.id = ?1")
    void updateStatusFalse(Long id);

    List<Rezervation> findByUser_IdEquals(Long id);

}

package sk.backend.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import sk.backend.server.domain.Rezervation;
import sk.backend.server.domain.User;

import java.util.List;
import java.util.Optional;

public interface RezervationJpaRepo extends JpaRepository<Rezervation, Long> {

    @Transactional
    @Modifying
    @Query("update Rezervation r set r.user = ?1 where r.id = ?2")
    void rezerveTerm(User user, Long id);
//    Optional<Rezervation> findFirstByCurrentTimeEqualsOrderByIdAscAndUserIsNullAndStatusIsTrue(String currentTime);

    Optional<Rezervation> findFirstByCurrentTimeEqualsAndStatusIsTrueAndUserIsNull(String currentTime);
//    @Transactional
//    @Modifying
//    @Query("update Rezervation r set r.user = ?1 where r.user is null and r.status = true")
//    void UpdateUser(User user);


//    Integer countByCurrentTimeEqualsAndStatusIsTrueAndUserIsNotNull(String currentTime);

    Integer countByCurrentTimeEqualsAndUserIsNotNullAndStatusIsTrue(String currentTime);

//    long countByUserAndCurrentTimeEqualsAndUserIsNotNull(String current_time);

    List<Rezervation> findByCurrentTimeEquals(String current_time);

    List<Rezervation> findByUserIsNullAndStatusIsTrue();


    //?1 co to je a vytvorenie aj zrusenia
//    @Transactional
//    @Modifying
//    @Query("update Rezervation r set r.user = ?1 where r.user is null")
//    void newUserRezervation(@NonNull User user);


}

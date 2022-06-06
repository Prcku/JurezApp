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

public interface RezervationJpaRepo extends JpaRepository<Rezervation, Long> {

    //toto asi nebude treba
//    List<Rezervation> findByUserIsNotNull();

    List<Rezervation> findByUserIsNull();


    //?1 co to je a vytvorenie aj zrusenia
    @Transactional
    @Modifying
    @Query("update Rezervation r set r.user = ?1 where r.user is null")
    void newUserRezervation(@NonNull User user);


}

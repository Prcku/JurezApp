package sk.server.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import sk.server.backend.domain.Rezervation;

import java.util.Date;
import java.util.List;

public interface RezervationJpaRepo extends JpaRepository<Rezervation,Long> {

    // najdi rezervacie ktore su este dostupne pre usera
    List<Rezervation> findByStatusTrue();

    // najdi userov ktory sa prave nachadzaju v posilnovni
    List<Rezervation> findByStatusFalse();

    //spocitaj rezercacie s danym casom
    Integer countByCurrentTimeEquals(Date currentTime);

    //sluzi na to aby si uzivatel rezervoval termin iba
//    @Query("select (count(r) > 0) from Rezervation r where r.currentTime = ?1 and r.user.id = ?2 GROUP BY function('date_format', r.currentTime, '%Y, %m, %d')")
//    boolean onlyOneRezervation(Date currentTime, Long id);

    //sluzi na to aby si uzivatel rezervoval termin za jeden den iba jeden krat
    @Query("select (count(r.currentTime) < 1) from Rezervation r where function('date_format', r.currentTime, '%Y, %m, %d') = function('date_format',?1, '%Y, %m, %d') and r.user.id = ?2 ")
    boolean onlyOneRezervation(Date currentTime, Long id);

    //zrusenie terminu
    void deleteByCurrentTimeEqualsAndUser_IdEquals(Date currentTime, Long id);

    //ak je rezervacia uz nedostupna na zaklade casu tak sa rezervacia uz neda rezervovat
    @Transactional
    @Modifying
    @Query("update Rezervation r set r.status = false where r.id = ?1")
    void updateStatusFalse(Long id);

    //najdi rezervaciu ktoru vlastni user s danym id a zoradi ich podla datumu
    List<Rezervation> findByUser_IdAllIgnoreCaseOrderByCurrentTimeAsc(Long id);

    @Transactional
    @Modifying
    @Query("update Rezervation r set r.status = false where function('date_format', r.currentTime, '%Y, %m, %d, %h, %m, %s') <= function('date_format',?1, '%Y, %m, %d, %h, %m, %s') ")
    void updateStatusToFalseBecouseOfTime(Date currentTime);

    List<Rezervation> findByCurrentTimeBetween(Date currentTimeStart, Date currentTimeEnd);




}

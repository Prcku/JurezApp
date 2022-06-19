package sk.server.backend.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;
import sk.server.backend.repo.RezervationJpaRepo;
import sk.server.backend.repo.UserJpaRepo;
import sk.server.backend.service.RezervationService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class RezervationServiceImpl implements RezervationService {

    @Autowired
    private final RezervationJpaRepo rezervationJpaRepo;

    @Autowired
    private final UserJpaRepo userJpaRepo;

    @Override
    public void create(Date date) {
        try {
            log.info("Create new Rezervation: {}",date);

            for (int i =0 ; i < 4; i++){
                Rezervation rezervation = new Rezervation();
                rezervation.setCurrentTime(date);
                rezervation.setStatus(true);
                log.info("create new Rezervation {}",i);
                rezervationJpaRepo.save(rezervation);
            }
        }catch (Exception e){
            log.info("Create new Rezervation FAILED error = {}", e.getMessage());
        }

    }

    //toto asi nebude treba
    @Override
    public Rezervation get(Long id) {
        try {
            Rezervation rezervation = rezervationJpaRepo.findById(id).get();
            log.info("Get rezeration: {}",id);
            return rezervation;
        }catch (Exception e){
            log.info("Get rezeration faild: {}",id);
            return null;
        }
    }

    @Override
    public List<Rezervation> findByDate(Date date) {
        try {
            List<Rezervation> rezervation = rezervationJpaRepo.findByCurrentTimeEquals(date);
            log.info("Get rezeration by Date: {}",date);
            return rezervation;
        }catch (Exception e){
            log.info("Get rezeration by Date faild {}",date);
            return null;
        }
    }

    @Override
    public List<Rezervation> availibleRezervation() {
        try{
            whatTimeIsIt();
            List<Rezervation> rezervations  =  rezervationJpaRepo.freeRezervation();
            log.info("Get freeRezervation {}", rezervations.size());
         return rezervations;
        }catch (Exception e){
            log.info("Get freeRezervation faild: {}",e.getMessage());
            return null;
        }
    }

    @Override
    public List<Rezervation> UserRezervations(Long id) {
        try {
            List<Rezervation> rezervations = rezervationJpaRepo.findByUser_IdEquals(id);
                log.info("Get user Rezervations");
                return rezervations;
        }catch (Exception e){
            log.info("Get user Rezervation failed");
            return  null;
        }
    }

    @Override
    public void rezerveTerm(Date date, Long id) {
        try{
            Optional<Rezervation> rezervation = rezervationJpaRepo.findFirstByCurrentTimeEqualsAndStatusTrueAndUserIsNullOrderByCurrentTimeAsc(date);
            log.info("Get first rezervation by date : {}", date);
            Optional<User> user = userJpaRepo.findById(id);
            log.info("Get user by id : {}", id);
            rezervationJpaRepo.rezerveTerm(user.get(),rezervation.get().getId());
            log.info("update rezervation : {}");
        }catch (Exception e){
            log.info("Get first rezervation by date FAILED: {}", date);
        }
    }

    @Override
    public Rezervation cancelRezervation(Date date, Long id) {
        try{
            Optional<Rezervation> rezervation = rezervationJpaRepo.findFirstByCurrentTimeEqualsAndStatusIsTrueAndUser_IdEquals(date,id);
            log.info("Get first rezervation by date = {}", date );
            log.info("and by user id = {}", id);
            rezervationJpaRepo.rezerveTerm(null,rezervation.get().getId());
            log.info("update rezervation : {}",rezervation.get().getId());
            return rezervationJpaRepo.findById(rezervation.get().getId()).get();
        }catch (Exception e){
            log.info("Get first rezervation by date FAILED: {}", date);
            return null;
        }
    }

    @Override
    public Integer howManyPlaceIsNotFree(Date currentTime) {
        try{
            Integer number = rezervationJpaRepo.countByCurrentTimeEqualsAndUserIsNotNullAndStatusIsTrue(currentTime);
            log.info("Get user of this rezervations: {}", currentTime);
            return number;
        }catch (Exception e){
            log.info("Get user of this rezervations faild: {}" , currentTime);
            return null;
        }
    }

    private void whatTimeIsIt(){
        Date now = new Date();
        log.info("check which rezervation already not availible");
        List<Rezervation> rezervations = rezervationJpaRepo.findAll();
        for (Rezervation rezervation:rezervations) {
            if (rezervation.getCurrentTime().getTime() < now.getTime()){
                rezervationJpaRepo.updateStatusFalse(rezervation.getId());
            }
        }
    }
}

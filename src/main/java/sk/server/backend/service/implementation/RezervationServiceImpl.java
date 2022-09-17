package sk.server.backend.service.implementation;

import org.apache.commons.lang.time.DateUtils;
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
import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class RezervationServiceImpl implements RezervationService {

    private Date lastNow = new Date(2022,9,7,12,0,0);

    @Autowired
    private final RezervationJpaRepo rezervationJpaRepo;

    @Autowired
    private final UserJpaRepo userJpaRepo;

    @Override
    public void create(Date date) {
        try {
            log.info("Create new Rezervation: {}",date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Date now = new Date();
            if(date.getTime() <= now.getTime()){
                throw new Exception();
            }
            else {
                calendar.add(Calendar.HOUR,5);
                for (int i=0 ; i < 17 ;i++){
                    calendar.add(Calendar.HOUR,1);
                    for (int j =0 ; j < 4; j++){
                        Rezervation rezervation = new Rezervation();
                        rezervation.setCurrentTime(calendar.getTime());
                        rezervation.setStatus(true);
                        log.info("create new Rezervation {}",j);
                        rezervationJpaRepo.save(rezervation);
                    }
                }
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
    public long findByDate(Date date) {
        try {
//            Calendar calendar = Calendar.getInstance();   // pre dodanie konkretneho casu
//            calendar.setTime(date);
//            Date now = new Date();
//            Date nearestMinute = DateUtils.round(now, Calendar.MINUTE);   // potrebne este podmienka na porovnanie casu
            log.info("search rezervation by date {}", date);
            return rezervationJpaRepo.countByCurrentTimeEquals(date);
        }catch (Exception e){
            log.info("Get rezeration by Date faild {}",date);
            return 0;
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
    public void rezerveTerm(Date date, Long id) {
        try{
            long fullRezervation = rezervationJpaRepo.countByCurrentTimeEquals(date);
            if (fullRezervation != 4 && !rezervationJpaRepo.onlyOneRezervation(date,id)){
                Optional<User> user = userJpaRepo.findById(id);
                log.info("Get user by id : {}", id);
                Rezervation rezervation = new Rezervation();
                rezervation.setCurrentTime(date);
                rezervation.setStatus(true);
                rezervation.setUser(user.get());
                rezervationJpaRepo.save(rezervation);
                log.info("update rezervation : {}");
            }else {
                log.info("rezervacie su mna tento cas full", date);
            }
        }catch (Exception e){
            log.info("Get first rezervation by date FAILED: {}", date);
        }
    }

    @Override
    public void cancelRezervation(Date date, Long id) {
        try{
            Optional<User> user = userJpaRepo.findById(id);
            log.info("Get user by id : {}", id);
            rezervationJpaRepo.deleteByCurrentTimeEqualsAndUserEquals(date,user);
            log.info("DELETE first rezervation by date SUCESS = {}", date );
        }catch (Exception e){
            log.info("DELETE first rezervation by date FAILED: {}", date);
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
    @Override
    public List<List<Date>> createRezervationByTime(){
        Date now = new Date();
        Date nearestDay = DateUtils.round(now, Calendar.DAY_OF_MONTH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nearestDay);
        if (now.getTime() < nearestDay.getTime()){
            calendar.add(Calendar.DATE,-1);
        }
        log.info("Created new session for excercies ... ");
        calendar.add(Calendar.MINUTE,285);
        List<List<Date>> kalendar2D = new ArrayList<>();
        for(int i=0; i<3;i++){
            List<Date> kalendar = new ArrayList<>();
            for (int j=0;j<13;j++){
                calendar.add(Calendar.MINUTE, 75);
                kalendar.add(calendar.getTime());

            }
            kalendar2D.add(kalendar);
            calendar.add(Calendar.MINUTE,465);
        }
            return kalendar2D;

    }
}

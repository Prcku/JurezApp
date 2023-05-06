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
import sk.server.backend.service.Response.RezervationDto;
import sk.server.backend.service.RezervationService;

import javax.transaction.Transactional;
import java.util.*;

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
    public List<Rezervation> findCurrentRezervation() {
        try {
            /*
                get now
                get rezervation date with status true
                compare with 1:15 + time
                return User which are there

             */
            Date now = new Date();
            List<Rezervation> rezervations;
            List<Rezervation> currentRezervations = new ArrayList<>();
            rezervations = rezervationJpaRepo.findByStatusFalse();
            for (Rezervation rezervation:rezervations) {
                Date endRezervation = new Date(rezervation.getCurrentTime().getTime() + 75*60*1000);
                if(rezervation.getCurrentTime().getTime() < now.getTime() && now.getTime() < endRezervation.getTime()){
                    currentRezervations.add(rezervation);
                }
            }
            return currentRezervations;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean rezerveRezervation(Date date, Long id) {
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            log.info("Rezerve rezervation ");
            calendar.add(Calendar.MINUTE,- 120);
            date = calendar.getTime();
            long fullRezervation = rezervationJpaRepo.countByCurrentTimeEquals(date);
            if (fullRezervation != 4 && rezervationJpaRepo.onlyOneRezervation(date,id)){
                Optional<User> user = userJpaRepo.findById(id);
                log.info("Get user by id : {}", id);
                Rezervation rezervation = new Rezervation();
                rezervation.setCurrentTime(date);
                rezervation.setStatus(true);
                rezervation.setUser(user.get());
                rezervationJpaRepo.save(rezervation);
                log.info("Rezervation was created : {}", rezervation.getCurrentTime());
                return true;
            }else {
                log.info("Rezervation are full on this time or you already have rezervation for today : {}" , date);
                return false;
            }
        }catch (Exception e){
            log.info("Get first rezervation by date FAILED: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public void cancelUserRezervation(Date date, Long id) {
        try{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            log.info("Rezerve rezervation ");
            calendar.add(Calendar.MINUTE,- 120);
            date = calendar.getTime();
            rezervationJpaRepo.deleteByCurrentTimeEqualsAndUser_IdEquals(date,id);
            log.info("DELETE first rezervation by date SUCCESS = {}", date );
        }catch (Exception e){
            log.info("DELETE first rezervation by date FAILED: {} excepotion -> {}", date,e.getMessage());
        }
    }

    @Override
    public List<RezervationDto> createRezervationByTime(Date date){
        Date now = new Date();
        //            For timezone
        Calendar calendarnow = Calendar.getInstance();
        calendarnow.setTime(now);
        log.info("Rezerve rezervation ");
        calendarnow.add(Calendar.MINUTE,- 120);
        now = calendarnow.getTime();
        ;
        Date nearestDay = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nearestDay);
        log.info("Created new session for excercies ... ");
        calendar.add(Calendar.MINUTE,240);
            List<RezervationDto> rezervationDtos = new ArrayList<>();
            for (int j=0;j<14;j++){
                RezervationDto rezervationDto = new RezervationDto();
                rezervationDto.setCurrentTime(calendar.getTime());
                if (rezervationDto.getCurrentTime().getTime() < now.getTime()){
                    rezervationDto.setStatus(false);
                }
                else {
                    rezervationDto.setStatus(true);
                }
                rezervationDto.setUsersCount(rezervationJpaRepo.countByCurrentTimeEquals(calendar.getTime()));
                rezervationDtos.add(rezervationDto);
                calendar.add(Calendar.MINUTE, 75);
        }
        log.info("check which rezervation already not availible");
            //treba otestovat ci to nahodou nebude chybat
//        for (Rezervation rezervation:rezervationJpaRepo.findByStatusTrue()) {
//            if (rezervation.getCurrentTime().getTime() < now.getTime()){
//                rezervationJpaRepo.updateStatusFalse(rezervation.getId());
//            }
//        }
        return rezervationDtos;

    }
}

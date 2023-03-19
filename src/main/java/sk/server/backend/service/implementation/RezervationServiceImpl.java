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
            long fullRezervation = rezervationJpaRepo.countByCurrentTimeEquals(date);
            if (fullRezervation != 4 && rezervationJpaRepo.onlyOneRezervation(date,id)){
                Optional<User> user = userJpaRepo.findById(id);
                log.info("Get user by id : {}", id);
                Rezervation rezervation = new Rezervation();
                rezervation.setCurrentTime(date);
                rezervation.setStatus(true);
                rezervation.setUser(user.get());
                rezervationJpaRepo.save(rezervation);
                log.info("update rezervation : {}");
                return true;
            }else {
                log.info("rezervacie su mna tento cas full alebo uz mas rezervaciu na tento den", date);
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
            rezervationJpaRepo.deleteByCurrentTimeEqualsAndUser_IdEquals(date,id);
            log.info("DELETE first rezervation by date SUCCESS = {}", date );
        }catch (Exception e){
            log.info("DELETE first rezervation by date FAILED: {} excepotion -> {}", date,e.getMessage());
        }
    }

    @Override
    public List<RezervationDto> createRezervationByTime(Date date){
        Date now = new Date();
        Date nearestDay = DateUtils.round(date, Calendar.DAY_OF_MONTH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nearestDay);
        if (now.getTime() < nearestDay.getTime()){
            calendar.add(Calendar.DATE,-1);
        }
        log.info("Created new session for excercies ... ");
        calendar.add(Calendar.MINUTE,360);
            List<RezervationDto> rezervationDtos = new ArrayList<>();
            for (int j=0;j<13;j++){
                calendar.add(Calendar.MINUTE, 75);
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

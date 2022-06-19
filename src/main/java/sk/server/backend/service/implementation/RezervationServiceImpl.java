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
    public Rezervation create(Rezervation rezervation) {
        log.info("Create new User: {}",rezervation.toString());
        rezervation.getUser().getId();
        return rezervationJpaRepo.save(rezervation);
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
//         List<Object> rezervations = rezervationJpaRepo.freeRezervation().stream().map(p -> (RezervationDto) p).collect(Collectors.toList());;
            List<Rezervation> rezervations  =  rezervationJpaRepo.freeRezervation();
//            for (RezervationDto rezervation : rezervations) {
//                System.out.println(rezervation.toString());
//            }
            log.info("Get freeRezervation: {}", rezervations.get(0));
//         for (RezervationDto rezervationDto: rezervations){
//             System.out.println(rezervationDto.toString());
//         }
         return rezervations;
        }catch (Exception e){
            System.out.println(e.getMessage());
            log.info("Get freeRezervation faild: {}");
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
            log.info("Get first rezervation by date {}", date , "and user id : {}",id);
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
        List<Rezervation> rezervations = rezervationJpaRepo.findAll();
        for (Rezervation rezervation:rezervations) {
            if (rezervation.getCurrentTime().getTime() < now.getTime()){
                rezervationJpaRepo.updateStatusFalse(rezervation.getId());
            }
        }
    }
}

package sk.backend.server.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.backend.server.domain.Rezervation;
import sk.backend.server.repo.RezervationJpaRepo;
import sk.backend.server.service.RezervationService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class RezervationServiceImpl implements RezervationService {

    @Autowired
    private final RezervationJpaRepo rezervationJpaRepo;

    @Override
    public Rezervation create(Rezervation rezervation) {
        log.info("Create new User: {}",rezervation.toString());
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
    public List<Rezervation> availibleRezervation() {
        try{
         List<Rezervation> rezervations = rezervationJpaRepo.findByUserIsNull();
            log.info("Get freeRezervation: {}");
         return rezervations;
        }catch (Exception e){
            log.info("Get freeRezervation faild: {}");
            return null;
        }
    }

    @Override
    public Rezervation rezerveTerm(Date date) {


        return null;
    }

    @Override
    public List<Rezervation> cancelRezervation(Rezervation rezervation) {
        return null;
    }
}

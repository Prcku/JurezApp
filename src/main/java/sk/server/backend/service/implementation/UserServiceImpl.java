package sk.server.backend.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;
import sk.server.backend.repo.RezervationJpaRepo;
import sk.server.backend.repo.UserJpaRepo;
import sk.server.backend.service.UserService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Autowired
    private RezervationJpaRepo rezervationJpaRepo;

    @Override
    public User create(User user) {
        try {
            if(userJpaRepo.findByEmailEquals(user.getEmail()).isEmpty()){
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
                User user1 = userJpaRepo.save(user);
                log.info("Create new User: {}",user.getEmail());
                return user1;
            }
            log.info("Duplicit Email: {}",user.getEmail());
            return null;
        }catch (Exception e){
            log.info("Create new User Failed: {}",user.getEmail());
            return null;
        }

    }

    @Override
    public User get(Long id) {
        try {
            User user = userJpaRepo.findById(id).get();
            log.info("Get user: {}",id);
            return user;
        }catch (Exception e){
            log.info("Get user faild: {}",id);
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        return userJpaRepo.findAll();
    }

    @Override
    public void update(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        userJpaRepo.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("delete User: {}",id);
        userJpaRepo.deleteById(id);
    }

    @Override
    public List<Rezervation> getUserRezervation(Long id) {
        try {
            Date now = new Date();
            Optional<User> userRezervation = userJpaRepo.findById(id);
            log.info("get User Rezervation by ID: {}",id);
            log.info("check which rezervation already not availible");
            for (Rezervation rezervation:userRezervation.get().getRezervations()) {
                if (rezervation.getCurrentTime().getTime() < now.getTime()){
                    rezervationJpaRepo.updateStatusFalse(rezervation.getId());
                }
            }
            return rezervationJpaRepo.findByUser_IdEquals(id);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        try {
            Optional<User> user = userJpaRepo.findByEmailEquals(email);
            return user.get();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public User authentification(String email, String password) {
        try {
            System.out.println(userJpaRepo.findByEmailEquals(email).get().getPassword());
            if (BCrypt.checkpw(password,userJpaRepo.findByEmailEquals(email).get().getPassword())){
                log.info("Password is Right");
                return userJpaRepo.findByEmailEquals(email).get();
          }
            else {
                log.info("Wrong password");
                return null;
            }
        }catch (Exception e){
            log.info("nieco je naozaj zle");
            return  null;
        }
    }

//    @Override
//    public User updateUserToken(String token, String email) {
//        try {
//            userJpaRepo.updateUserToken(token,email);
//            return userJpaRepo.findByEmailEquals(email).get();
//        }catch (Exception e){
//            return null;
//        }
//    }
}

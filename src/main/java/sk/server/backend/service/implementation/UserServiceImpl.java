package sk.server.backend.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;
import sk.server.backend.repo.UserJpaRepo;
import sk.server.backend.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepo userJpaRepo;

//    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserServiceImpl() {
        User user = new User();
        if(getByEmail("admin@admin.sk") == null) {
            user.setFirstName("Admin");
            user.setLastName("Pan");
            user.setEmail("admin@admin.sk");
            user.setPassword("admin123");
            create(user);
        }
    }

//    public UserServiceImpl(UserJpaRepo userJpaRepo) {
//        this.userJpaRepo = userJpaRepo;
//    }

    @Override
    public User create(User user) {
        try {
            if(userJpaRepo.findByEmailEquals(user.getEmail()).isEmpty()){
//                user.setPassword(this.passwordEncoder.encode(user.getPassword()));
                User user1 = userJpaRepo.save(user);
//                user.setPassword();
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
            Optional<User> userRezervation = userJpaRepo.findById(id);
            log.info("get User Rezervation by ID: {}",id);
            return userRezervation.get().getRezervations();
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
            return userJpaRepo.findByEmailEqualsAndPasswordEquals(email,password).get();
        }catch (Exception e){
            return  null;
        }
    }
}

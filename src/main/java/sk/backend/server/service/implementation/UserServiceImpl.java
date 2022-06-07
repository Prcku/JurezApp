package sk.backend.server.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.backend.server.domain.Rezervation;
import sk.backend.server.domain.User;
import sk.backend.server.repo.UserJpaRepo;
import sk.backend.server.service.UserService;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private final UserJpaRepo userJpaRepo;

    @Override
    public User create(User user) {
        try {
            if(userJpaRepo.findByEmailEquals(user.getEmail()).isEmpty()){
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
        userJpaRepo.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("delete User: {}",id);
        userJpaRepo.deleteById(id);
    }

    @Override
    public List<Rezervation> getUserRezervation(Long id) {
        Optional<User> userRezervation = userJpaRepo.findById(id);

        return null;
    }
}

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
        log.info("Create new User: {}",user.getEmail());
        return userJpaRepo.save(user);
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
    public User update(User user) {
        return userJpaRepo.save(user);
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

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
import java.util.*;


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
                user.setRole("ROLE_WATCHER");
                User user1 = userJpaRepo.save(user);
                if (user1 != null) {
                    log.info("Create new User: {}",user1.getEmail());
                    return user1;
                }
                log.info("Not create new User: {}",user1.getEmail());
                return null;
            }
            log.info("Duplicit Email: {}",user.getEmail());
            return null;
        }catch (Exception e){
            log.info("Create new User Failed: {}",user.getEmail());
            return null;
        }

    }

    @Override
    public List<User> findUsersInGym() {
        try {
            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();
            Date startTime = new Date(timeInSecs - (75 * 60 * 1000));
            Date endTime = new Date();
            log.info("Searching user in Current time , {} ",endTime);
            return userJpaRepo.findByRezervations_CurrentTimeBetweenOrderByRezervations_CurrentTimeAsc(startTime,endTime);

        }catch (Exception e){
            return null;
        }
    }

    //musi tu prichadzat parameter napriklad 00:00 a v tomto case vyhadze
    @Override
    public HashMap<Date, List<Optional<User>>> findAllUsersInGym(Date startTime){
        try {
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(startTime);
            Calendar writeTime = Calendar.getInstance();
            writeTime.setTime(startTime);
            log.info("Searching user in Current day , {} ",endTime.getTime());
            HashMap<Date,List<Optional<User>>> map = new HashMap<>();
            for (int i =0;i < 14;i++){
                endTime.add(Calendar.MINUTE, 74);
                List<Rezervation> rezervations = rezervationJpaRepo.findByCurrentTimeBetween(startTime,endTime.getTime());
                List<Optional<User>> users = new ArrayList<>();
                for (Rezervation rezervation:rezervations) {
                    users.add(userJpaRepo.findById(rezervation.getUser().getId()));
                };
                startTime.setTime(endTime.getTimeInMillis() + (i * 60 * 1000));
                map.put(writeTime.getTime(),users);
                startTime = endTime.getTime();
                writeTime.add(Calendar.MINUTE,75);
            }
            System.out.println(map);
            return map;
        }catch (Exception e){
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
    public boolean update(User user) {
        try {
            System.out.println(user.toString());
            if (Objects.equals(user.getPassword(), userJpaRepo.findByEmailEquals(user.getEmail()).get().getPassword())){
                log.info("Password was the same ");
                userJpaRepo.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),user.getRole() ,user.getId());
                return true;
            }
            else {
                log.info("Password not the same create new one");
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
                userJpaRepo.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),user.getRole() ,user.getId());
                return true;
            }
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public void delete(Long id) {
        log.info("delete User: {}",id);
        userJpaRepo.deleteById(id);
    }

    @Override
    public List<Rezervation> getUserRezervation(Long id) {
        try {
            log.info("Get User Rezervation {}",id);
            rezervationJpaRepo.updateStatusToFalseBecouseOfTime(new Date());
            System.out.println(new Date());
            return rezervationJpaRepo.findByUser_IdAllIgnoreCaseOrderByCurrentTimeAsc(id);
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
//            if (Objects.equals(password, userJpaRepo.findByEmailEquals(email).get().getPassword())){
//                log.info("Password is Right");
//                return userJpaRepo.findByEmailEquals(email).get();
//            }
//            else {
//                return null;
//            }
            if (BCrypt.checkpw(password,userJpaRepo.findByEmailEquals(email).get().getPassword())){
                log.info("Password is Right");
                return userJpaRepo.findByEmailEquals(email).get();
            }
            else {
                log.info("Wrong password");
                return null;
            }
        }catch (Exception e){
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

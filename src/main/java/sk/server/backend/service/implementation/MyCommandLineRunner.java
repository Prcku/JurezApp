package sk.server.backend.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sk.server.backend.domain.User;
import sk.server.backend.repo.UserJpaRepo;
import sk.server.backend.service.UserService;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.TimeZone;

@Transactional
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Override
    public void run(String... args) {
        System.out.println("Timezone: " + TimeZone.getDefault().getID());
        if(userJpaRepo.findByEmailEquals("junak@junak.com").isEmpty()){
        User first_admin = new User();
        first_admin.setFirstName("Branislav");
        first_admin.setLastName("Junak");
        first_admin.setPassword(BCrypt.hashpw("123", BCrypt.gensalt(12)));
        first_admin.setEmail("junak@junak.com");
        first_admin.setRole("ROLE_ADMIN");
        User user1 = userJpaRepo.save(first_admin);
        if (user1 != null) {
            log.info("Create new User: {}", user1.getEmail());
        }
        log.info("Not create new User: {}", user1.getEmail());
        }
    }
}

package sk.server.backend.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import sk.server.backend.domain.User;
import sk.server.backend.repo.UserJpaRepo;
import sk.server.backend.service.UserService;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Transactional
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserJpaRepo userJpaRepo;

    @Override
    public void run(String... args) {
        if(userJpaRepo.findByEmailEquals("junak@junak.com").isEmpty()){
        User first_admin = new User();
        first_admin.setFirstName("Branislav");
        first_admin.setLastName("Junak");
        first_admin.setPassword("123");
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

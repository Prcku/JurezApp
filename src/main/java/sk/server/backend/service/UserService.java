package sk.server.backend.service;

import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface UserService {
    User create(User user);
    User get(Long id);
    List<User> getAll();
    void update(User user);
    void delete(Long id);
    List<Rezervation> getUserRezervation(Long id);
    User getByEmail(String email);
    User authentification(String email, String password);
    List<User>findUsersInGym();
    List<User>findAllUsersInGym(Date startTime);
}

package sk.server.backend.service;

import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    User get(Long id);
    List<User> getAll();
    boolean update(User user);
    void delete(Long id);
    List<Rezervation> getUserRezervation(Long id);
    User getByEmail(String email);
    User authentification(String email, String password);
    List<User>findUsersInGym();
    HashMap<Date, List<Optional<User>>> findAllUsersInGym(Date startTime);
}

package sk.server.backend.service;

import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;

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
    User updateUserToken(String token,String email);
}

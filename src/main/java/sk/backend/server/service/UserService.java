package sk.backend.server.service;

import sk.backend.server.domain.Rezervation;
import sk.backend.server.domain.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User get(Long id);
    List<User> getAll();
    User update(User user);
    void delete(Long id);
    List<Rezervation> getUserRezervation(Long id);
}

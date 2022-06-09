package sk.server.backend.controller;

import org.springframework.web.bind.annotation.*;
import sk.server.backend.controller.exceptions.BadRequestException;
import sk.server.backend.controller.exceptions.EntityNotFoundException;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;
import sk.server.backend.service.UserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource()
    private UserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        Optional<User> user = Optional.ofNullable(userService.get(id));
        return user.orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("rezervation/{id}")
    public List<Rezervation> getUserRezervation(@PathVariable Long id){
        return userService.getUserRezervation(id);

    }

    @PostMapping()
    public User createUser(@RequestBody User user) throws BadRequestException{
        if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null){
            throw new BadRequestException();
        }
        Optional<User> user1 = Optional.ofNullable(userService.create(user));
        return user1.orElseThrow(BadRequestException::new);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id){
        User user1 = userService.get(id);
        if (user1 == null){
            return null;
        }
        if( user.getFirstName() != null){
            user1.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null){
            user1.setLastName(user.getLastName());
        }
        if(user.getEmail() != null ){
            user1.setEmail(user1.getEmail());
        }
        userService.update(user1);
        return userService.get(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
         userService.delete(id);
    }

}
package sk.backend.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.backend.server.domain.User;
import sk.backend.server.service.UserService;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

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
        return userService.get(id);
    }

    @PostMapping(
//            consumes = {MediaType.APPLICATION_JSON_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE}
            )
    public User createUser(@RequestBody User user){
        return userService.create(user);
    }

    // treba dorobit ID a update pre usera ostatne
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
//        userService.update(user);
        userService.update(user1);
        return userService.get(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
         userService.delete(id);
    }

}

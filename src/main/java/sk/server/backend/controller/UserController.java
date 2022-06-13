package sk.server.backend.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import sk.server.backend.controller.exceptions.BadRequestException;
import sk.server.backend.controller.exceptions.ConflictException;
import sk.server.backend.controller.exceptions.EntityNotFoundException;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;
import sk.server.backend.service.UserService;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("api/user")
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

    @GetMapping("/{email}")
    public User getUser(@PathVariable String email){
        Optional<User> user = Optional.ofNullable(userService.getByEmail(email));
        return user.orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("rezervation/{id}")
    public List<Rezervation> getUserRezervation(@PathVariable Long id){
        return userService.getUserRezervation(id);

    }

    @PostMapping()
    public User createUser(@RequestBody User user){
        if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null){
            throw new BadRequestException();
        }
        Optional<User> user1 = Optional.ofNullable(userService.create(user));
        return user1.orElseThrow(ConflictException::new);
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
            user1.setEmail(user.getEmail());
        }
        if(user.getPassword() != null){
            user1.setPassword(user.getPassword());
        }
        userService.update(user1);
        return userService.get(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
         userService.delete(id);
    }

    //zmenil si return y boolean na user
    @PostMapping("/auth")
    public User authentificationUser(@RequestParam("email") String email,@RequestParam("password") String password){
        if (email == null || password == null){
            throw new BadRequestException();
        }
        if (userService.authentification(email, password) != null){
            String token = getJWTToken(email);
            User user = userService.getByEmail(email);
            user.setToken(token);
            return user;
        }
        else {
            throw new EntityNotFoundException();
        }
    }
    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}

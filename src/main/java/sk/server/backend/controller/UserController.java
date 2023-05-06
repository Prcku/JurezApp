package sk.server.backend.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import scala.util.hashing.Hashing;
import sk.server.backend.controller.exceptions.BadRequestException;
import sk.server.backend.controller.exceptions.ConflictException;
import sk.server.backend.controller.exceptions.EntityNotFoundException;
import sk.server.backend.controller.exceptions.NoContentException;
import sk.server.backend.controller.secure.PasswordHashingUtils;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;
import sk.server.backend.service.Response.UserDto;
import sk.server.backend.service.UserService;


import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin(origins = "https://prcku.github.io")
@RequestMapping("api/user")
public class UserController {

    @Resource()
    private UserService userService;

    @GetMapping
//    doriesit vratenie chybi v prípade niečoho zlého
    public List<User> getUsers(){
        try {
            return userService.getAll();
        }catch (Exception e){
            throw new NoContentException();
        }
    }

    @GetMapping("/currentrezervation")
    public List<User> getUsersInGym(){
        try {
            return userService.findUsersInGym();
        }catch (Exception e){
            throw new NoContentException();
        }
    }

    @GetMapping("/currentrezervation/{time}")
    public HashMap<Date, List<Optional<User>>> getAllUsersInGym(@PathVariable String time){
        if (time == null){
            throw new BadRequestException();
        }
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
            return userService.findAllUsersInGym(date);
        }catch (Exception e){
            throw new BadRequestException();
        }

    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        Optional<User> user = Optional.ofNullable(userService.get(id));
        return user.orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("email/{email}")
    public User getUserByEmail(@PathVariable String email){
        Optional<User> user = Optional.ofNullable(userService.getByEmail(email));
        return user.orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping("rezervation/{id}")
    public List<Rezervation> getUserRezervation(@PathVariable Long id){
        try {
            return userService.getUserRezervation(id);
        }catch (Exception e){
            throw new EntityNotFoundException();
        }

    }

    @PostMapping()
    public User createUser(@RequestBody User user){
        if(user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null){
            throw new BadRequestException();
        }
        Optional<User> user1 = Optional.ofNullable(userService.create(user));
        return user1.orElseThrow(ConflictException::new);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id){
        User user1 = user;
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
        if(user.getRole() != null){
            user1.setRole(user.getRole());
        }
        try {
            userService.update(user1);
            return userService.get(id);
        }catch (Exception e) {
            throw new ConflictException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        try {
            userService.delete(id);
        }catch (Exception e){
            throw new EntityNotFoundException();
        }
    }

    //zmenil si return y boolean na user
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/auth", produces = "text/plain")
    public String authentificationUser( @RequestBody UserDto user){

        if (user.getEmail() == null || user.getPassword() == null){
            throw new BadRequestException();
        }
//        String hashedPassword = Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString();
//        String hashedPassword =  PasswordHashingUtils.hashPassword(user.getPassword());
//        System.out.println(hashedPassword);
        User user1 = userService.authentification(user.getEmail(),user.getPassword());
        if (user1 != null){
            return getJWTToken(user1);
        }
        else {
            throw new BadRequestException();
        }
    }
    private String getJWTToken(User user) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(user.getRole());

        String token = Jwts
                .builder()
                .setId(String.valueOf(user.getId()))
                .setSubject(user.getEmail())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                toto zmenit ak budem chciet auto loggout ale pridat tam trosku viac milisekund
              //  .setExpiration(new Date(System.currentTimeMillis() + 1200000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}

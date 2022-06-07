package sk.backend.server.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sk.backend.server.domain.Rezervation;
import sk.backend.server.domain.User;
import sk.backend.server.service.RezervationService;
import sk.backend.server.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/rezervation")
public class RezervationController {


        @Resource()
        private RezervationService rezervationService;


//        @GetMapping
//        public List<User> getUsers(){
//            return userService.getAll();
//        }

        @GetMapping("/{id}")
        public Rezervation getRezervation(@PathVariable Long id){
            return rezervationService.get(id);
        }

        @GetMapping("/free")
        public List<Rezervation> getAvalivble(){
            return rezervationService.availibleRezervation();
        }

        @GetMapping("/time/{date}")
        public List<Rezervation> getRezervationByDate(@PathVariable String date){
            return rezervationService.findByDate(date);
        }

        @GetMapping("/free/{date}")
        public Integer gethowManyPlaceIsNotFree(@PathVariable String date){
            return rezervationService.howManyPlaceIsNotFree(date);
        }

        @PostMapping(
                consumes = {MediaType.APPLICATION_JSON_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE})
        public Rezervation createRezervation(@RequestBody Rezervation rezervation){
            return rezervationService.create(rezervation);
        }

        @PutMapping("/time/{date}/{id}")
        public Rezervation bookRezervation(@PathVariable String date,@PathVariable Long id){
            Rezervation rezervation = rezervationService.rezerveTerm(date,id);
            return  rezervation;
        }

//        @DeleteMapping("/{id}")
//        public void deleteUser(@PathVariable Long id){
//            userService.delete(id);
//        }

}

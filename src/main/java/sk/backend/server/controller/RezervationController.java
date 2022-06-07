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
        public List<Rezervation> getAvalible(){
            return rezervationService.availibleRezervation();
        }

        @GetMapping("/time/{date}")
        public List<Rezervation> getRezervationByDate(@PathVariable String date){
            return rezervationService.findByDate(date);
        }

        @PutMapping("/time/{date}/{id}")
        public Rezervation bookRezervation(@PathVariable String date,@PathVariable Long id){
            return rezervationService.rezerveTerm(date,id);
        }

        @GetMapping("/free/{date}")
        public Integer gethowManyPlaceIsNotFree(@PathVariable String date){
            return rezervationService.howManyPlaceIsNotFree(date);
        }

        @PostMapping()
        public Rezervation createRezervation(@RequestBody Rezervation rezervation){
            return rezervationService.create(rezervation);
        }

        @DeleteMapping("time/{date}/{id}")
        public void deleteUser(@PathVariable String date ,@PathVariable Long id){
            rezervationService.cancelRezervation(date, id);
        }

}

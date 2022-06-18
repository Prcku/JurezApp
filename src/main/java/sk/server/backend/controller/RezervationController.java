package sk.server.backend.controller;

import org.springframework.web.bind.annotation.*;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.service.RezervationService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/rezervation")
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
        public List<Rezervation> getRezervationByDate(@PathVariable Date date){
            return rezervationService.findByDate(date);
        }

        @PutMapping("/time/{date}/{id}")
        public Rezervation bookRezervation(@PathVariable Date date,@PathVariable Long id){
            return rezervationService.rezerveTerm(date,id);
        }

        @GetMapping("/free/{date}")
        public Integer gethowManyPlaceIsNotFree(@PathVariable Date date){
            return rezervationService.howManyPlaceIsNotFree(date);
        }

        @PostMapping()
        public Rezervation createRezervation(@RequestBody Rezervation rezervation){
            return rezervationService.create(rezervation);
        }

        @PutMapping("time/cancel/{date}/{id}")
        public void deleteUser(@PathVariable Date date ,@PathVariable Long id){
            rezervationService.cancelRezervation(date, id);
        }
        @GetMapping("/user/{id}")
        public List<Rezervation> getUserRezervation(@PathVariable Long id){
            return rezervationService.UserRezervations(id);
        }

}

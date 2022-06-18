package sk.server.backend.controller;

import org.springframework.web.bind.annotation.*;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.service.RezervationService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        public List<Rezervation> getRezervationByDate(@PathVariable String date) throws ParseException {
            Date date1 = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm").parse(date);
            return rezervationService.findByDate(date1);
        }

        @PutMapping("/time/{id}/{date}")
        public void bookRezervation(@PathVariable String date,@PathVariable Long id) throws ParseException {
            Date date1 = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm").parse(date);
            rezervationService.rezerveTerm(date1,id);
        }

        @GetMapping("/free/{date}")
        public Integer gethowManyPlaceIsNotFree(@PathVariable String date) throws ParseException {
            Date date1 = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm").parse(date);
            return rezervationService.howManyPlaceIsNotFree(date1);
        }

        @PostMapping()
        public Rezervation createRezervation(@RequestBody Rezervation rezervation){
            return rezervationService.create(rezervation);
        }

        @PutMapping("time/cancel/{date}/{id}")
        public void deleteUser(@PathVariable String date ,@PathVariable Long id) throws ParseException {
            Date date1 = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm").parse(date);
            rezervationService.cancelRezervation(date1, id);
        }
        @GetMapping("/user/{id}")
        public List<Rezervation> getUserRezervation(@PathVariable Long id){
            return rezervationService.UserRezervations(id);
        }

}

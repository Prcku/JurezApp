package sk.server.backend.controller;

import org.springframework.web.bind.annotation.*;
import sk.server.backend.controller.exceptions.BadRequestException;
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
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
            return rezervationService.findByDate(date1);
        }

        @PutMapping("/time/{id}/{date}")
        public void bookRezervation(@PathVariable String date,@PathVariable Long id)  {
            if (id == null || date == null){
                throw new BadRequestException();
            }
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
                rezervationService.rezerveTerm(date1,id);
            }catch (Exception e){
                throw new BadRequestException();
            }

        }

        @GetMapping("/free/{date}")
        public Integer gethowManyPlaceIsNotFree(@PathVariable String date)  {
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
                return rezervationService.howManyPlaceIsNotFree(date1);
            }
            catch (Exception e){
                throw new BadRequestException();
            }
        }

        @PostMapping()
        public void createRezervation(@RequestBody String date) {
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                rezervationService.create(date1);
            }catch (Exception e){
                throw new BadRequestException();
            }
        }

        @PutMapping("time/cancel/{date}/{id}")
        public void deleteUser(@PathVariable String date ,@PathVariable Long id){
            if (date == null || id == null){
                throw new BadRequestException();
            }
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
                rezervationService.cancelRezervation(date1, id);
            }catch (Exception e){
                throw new BadRequestException();
            }
        }
        @GetMapping("/user/{id}")
        public List<Rezervation> getUserRezervation(@PathVariable Long id){
            return rezervationService.UserRezervations(id);
        }

}

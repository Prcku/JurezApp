package sk.server.backend.controller;

import org.springframework.web.bind.annotation.*;
import sk.server.backend.controller.exceptions.BadRequestException;
import sk.server.backend.domain.Rezervation;
import sk.server.backend.service.Response.RezervationDto;
import sk.server.backend.service.RezervationService;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/rezervation")
public class RezervationController {

        @Resource()
        private RezervationService rezervationService;

//        @GetMapping("/{id}")
//        public Rezervation getRezervation(@PathVariable Long id){
//            return rezervationService.get(id);
//        }

        @GetMapping("/free")
        public List<Rezervation> getAvalible(){
            return rezervationService.availibleRezervation();
        }

        @GetMapping("/kalendar")
        public List<List<RezervationDto>> getNewTimes(){return rezervationService.createRezervationByTime();}

        @GetMapping("/time/{date}")
        public long getRezervationByDate(@PathVariable String date) throws ParseException {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
            return rezervationService.findByDate(date1);
        }

        @PostMapping("/time/{id}/{date}")
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

//        @PostMapping()
//        public void createRezervation(@RequestBody String date) {
//            try {
//                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//                rezervationService.create(date1);
//            }catch (Exception e){
//                throw new BadRequestException();
//            }
//        }

        @DeleteMapping("time/cancel/{date}/{id}")
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

}

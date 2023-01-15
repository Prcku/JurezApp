package sk.server.backend.controller;

import org.springframework.web.bind.annotation.*;
import sk.server.backend.controller.exceptions.BadRequestException;
import sk.server.backend.controller.exceptions.ConflictException;
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

//        @GetMapping("/free")
//        public List<Rezervation> getAvalible(){
//            return rezervationService.availibleRezervation();
//        }

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
                System.out.println("preco som tu <???  aslasasldasdasdasd");
                throw new BadRequestException();
            }
            Date date1;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
            }catch (Exception e){
                throw new BadRequestException();
            }
            if (!rezervationService.rezerveTerm(date1, id)){
                throw new ConflictException();
            }
        }

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

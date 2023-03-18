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

@RestController
@RequestMapping("api/rezervation")
public class RezervationController {

        @Resource()
        private RezervationService rezervationService;

        @GetMapping("/kalendar/{date}")
        public List<List<RezervationDto>> getNewRezervation(@PathVariable String date){
            if (date == null){
                throw new BadRequestException();
            }
            Date date1;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
                return rezervationService.createRezervationByTime(date1);
            }catch (Exception e){
                throw new BadRequestException();
            }
        }

        @GetMapping("/time")
        public List<Rezervation> getCurrentRezervation(){
            return rezervationService.findCurrentRezervation();
        }

        @PostMapping("/time/{id}/{date}")
        public void rezerveRezervation(@PathVariable String date,@PathVariable Long id)  {
            if (id == null || date == null){
                throw new BadRequestException();
            }
            Date date1;
            try {
                date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
            }catch (Exception e){
                throw new BadRequestException();
            }
            if (!rezervationService.rezerveRezervation(date1, id)){
                throw new ConflictException();
            }
        }

        @DeleteMapping("time/cancel/{date}/{id}")
        public void cancelUserRezervation(@PathVariable String date ,@PathVariable Long id){
            if (date == null || id == null){
                throw new BadRequestException();
            }
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
                rezervationService.cancelUserRezervation(date1, id);
            }catch (Exception e){
                throw new BadRequestException();
            }
        }


}

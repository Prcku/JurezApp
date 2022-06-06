package sk.backend.server.service.Response.factory;

import sk.backend.server.controller.RezervationController;
import sk.backend.server.domain.Rezervation;
import sk.backend.server.service.Response.RezervationDto;

public class RezervationResponseFactory {

    public RezervationDto transformToDto(Rezervation rezervation){
        RezervationDto rezervationDto = new RezervationDto();
        rezervationDto.setId(rezervation.getId());
        rezervationDto.setCurrent_time(rezervation.getCurrent_time());
        rezervationDto.setStatus(rezervation.getStatus());
        rezervationDto.setUser(rezervation.getUser());
        return rezervationDto;
    }

    public Rezervation transformToEntity(RezervationDto rezervationDto){
        Rezervation rezervation = new Rezervation();
        rezervation.setId(rezervation.getId());
        rezervation.setCurrent_time(rezervation.getCurrent_time());
        rezervation.setStatus(rezervation.getStatus());
        rezervation.setUser(rezervation.getUser());

        return null;
    }

}

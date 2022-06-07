package sk.backend.server.service.Response.factory;

import sk.backend.server.domain.Rezervation;
import sk.backend.server.service.Response.RezervationDto;

public class RezervationResponseFactory {

    public RezervationDto transformToDto(Rezervation rezervation){
        RezervationDto rezervationDto = new RezervationDto();
        rezervationDto.setId(rezervation.getId());
        rezervationDto.setCurrentTime(rezervation.getCurrentTime());
        rezervationDto.setStatus(rezervation.getStatus());
        rezervationDto.setUser(rezervation.getUser());
        return rezervationDto;
    }

    public Rezervation transformToEntity(RezervationDto rezervationDto){
        Rezervation rezervation = new Rezervation();
        rezervation.setId(rezervation.getId());
        rezervation.setCurrentTime(rezervation.getCurrentTime());
        rezervation.setStatus(rezervation.getStatus());
        rezervation.setUser(rezervation.getUser());

        return null;
    }

}

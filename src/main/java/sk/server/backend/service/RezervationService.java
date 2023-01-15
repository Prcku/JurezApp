package sk.server.backend.service;

import sk.server.backend.service.Response.RezervationDto;

import java.util.Date;
import java.util.List;

public interface RezervationService {
//    void create(Date rezervation);
//    Rezervation get(Long id);
//    List<Rezervation> availibleRezervation();
    boolean rezerveTerm(Date date, Long id);
    void cancelRezervation(Date date, Long id);
    long findByDate(Date date);
    List<List<RezervationDto>> createRezervationByTime();
}

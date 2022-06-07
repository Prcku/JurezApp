package sk.backend.server.service;

import sk.backend.server.domain.Rezervation;
import sk.backend.server.domain.User;

import java.util.Date;
import java.util.List;

public interface RezervationService {
    Rezervation create(Rezervation rezervation);
    Rezervation get(Long id);
    List<Rezervation> availibleRezervation();
//    Rezervation rezerveTerm(Date date, Long id);

    Rezervation rezerveTerm(String date, Long id);

    List<Rezervation> cancelRezervation(Rezervation rezervation);
    Integer howManyPlaceIsNotFree(String current_time);
    List<Rezervation> findByDate(String date);

}

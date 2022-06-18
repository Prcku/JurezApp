package sk.server.backend.service;

import sk.server.backend.domain.Rezervation;

import java.util.Date;
import java.util.List;

public interface RezervationService {
    Rezervation create(Rezervation rezervation);
    Rezervation get(Long id);
    List<Rezervation> availibleRezervation();
//    Rezervation rezerveTerm(Date date, Long id);
    List<Rezervation> UserRezervations(Long id);
    Rezervation rezerveTerm(Date date, Long id);
    Rezervation cancelRezervation(Date date, Long id);
    Integer howManyPlaceIsNotFree(Date current_time);
    List<Rezervation> findByDate(Date date);
}

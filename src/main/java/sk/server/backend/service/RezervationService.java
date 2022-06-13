package sk.server.backend.service;

import sk.server.backend.domain.Rezervation;

import java.util.List;

public interface RezervationService {
    Rezervation create(Rezervation rezervation);
    Rezervation get(Long id);
    List<Rezervation> availibleRezervation();
//    Rezervation rezerveTerm(Date date, Long id);
    List<Rezervation> UserRezervations(Long id);
    Rezervation rezerveTerm(String date, Long id);
    Rezervation cancelRezervation(String date, Long id);
    Integer howManyPlaceIsNotFree(String current_time);
    List<Rezervation> findByDate(String date);
}

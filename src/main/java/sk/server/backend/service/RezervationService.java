package sk.server.backend.service;

import sk.server.backend.domain.Rezervation;

import java.util.Date;
import java.util.List;

public interface RezervationService {
    void create(Date rezervation);
    Rezervation get(Long id);
    List<Rezervation> availibleRezervation();
    void rezerveTerm(Date date, Long id);
    Rezervation cancelRezervation(Date date, Long id);
    List<Rezervation> findByDate(Date date);
}

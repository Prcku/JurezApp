package sk.server.backend.service;

import sk.server.backend.domain.Rezervation;
import sk.server.backend.service.Response.RezervationDto;

import java.util.Date;
import java.util.List;

public interface RezervationService {
    boolean rezerveRezervation(Date date, Long id);
    void cancelUserRezervation(Date date, Long id);
    List<Rezervation> findCurrentRezervation();
    List<List<RezervationDto>> createRezervationByTime();
}

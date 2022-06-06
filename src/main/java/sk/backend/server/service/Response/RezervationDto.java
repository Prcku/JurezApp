package sk.backend.server.service.Response;

import lombok.Data;
import sk.backend.server.domain.User;

import javax.persistence.*;
import java.util.Date;

@Data
public class RezervationDto {

    private Long id;

//    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "Europe/Bratislava")
    private String current_time;

    private Boolean status;

    private User user;
}

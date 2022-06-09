package sk.server.backend.service.Response;

import lombok.Data;
import sk.server.backend.domain.User;

@Data
public class RezervationDto {

    private Long id;

//    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING, timezone = "Europe/Bratislava")
    private String currentTime;

    private Boolean status;

    private User user;
}

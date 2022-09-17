package sk.server.backend.service.Response;

import lombok.Data;
import java.sql.Date;


@Data
public class RezervationDto {

    private Date currentTime;

    private Long usersCount;

}

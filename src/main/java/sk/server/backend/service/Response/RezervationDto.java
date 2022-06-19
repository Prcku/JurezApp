package sk.server.backend.service.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import sk.server.backend.domain.User;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Date;
import java.sql.Timestamp;

@Data
public class RezervationDto {

//    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING, timezone = "Europe/Bratislava")
    private Date currentTime;

    private Long usersCount;

    public RezervationDto( Long usersCount, Date currentTime) {
        this.currentTime = currentTime;
        this.usersCount = usersCount;
    }

    public RezervationDto() {
    }
}

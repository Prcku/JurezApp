package sk.server.backend.service.Response;

import lombok.Data;

import java.util.Date;


@Data
public class RezervationDto {

    private Date currentTime;

    private Integer usersCount;

    private Boolean status;


    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

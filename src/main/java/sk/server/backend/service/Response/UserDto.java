package sk.server.backend.service.Response;

import lombok.Data;
import sk.server.backend.domain.Rezervation;

import java.util.List;

@Data
public class UserDto {

    private String email;
    private String password;
}

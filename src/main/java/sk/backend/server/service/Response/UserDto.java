package sk.backend.server.service.Response;

import lombok.Data;
import sk.backend.server.domain.Rezervation;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Rezervation> rezervations;
}

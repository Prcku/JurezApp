package sk.server.backend.service.Response;

import lombok.Data;
import sk.server.backend.domain.Rezervation;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Rezervation> rezervations;
}

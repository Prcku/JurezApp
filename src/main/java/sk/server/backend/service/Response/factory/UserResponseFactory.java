package sk.server.backend.service.Response.factory;

import sk.server.backend.domain.Rezervation;
import sk.server.backend.domain.User;
import sk.server.backend.service.Response.UserDto;

import java.util.ArrayList;
import java.util.List;

public class UserResponseFactory {
    public UserDto transformToDto(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        List<Rezervation> rezervationsList = new ArrayList<>();
        for (Rezervation item: user.getRezervations()){
            rezervationsList.add(item);
        }
        dto.setRezervations(rezervationsList);
        return dto;
    }

    public User transformToEntity(UserDto userDto){
        User user = new User();
        user.setId(user.getId());
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getFirstName());
        user.setEmail(user.getEmail());
        List<Rezervation> rezervationList = new ArrayList<>();
        for (Rezervation item: user.getRezervations()){
            rezervationList.add(item);
        }
        user.setRezervations(userDto.getRezervations());
        return user;
    }
}

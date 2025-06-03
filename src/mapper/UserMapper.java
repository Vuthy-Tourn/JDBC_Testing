package mapper;

import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.entities.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class UserMapper {
    public static UserResponseDto mapFromUserToUserResponseDto(User User){
        return new UserResponseDto(User.getUuid()
                , User.getUserName(), User.getEmail(),User.getCreatedDate());
    }
    public static User mapFromUserCreateDtoToUser(UserCreateDto userCreateDto){
        return new User(new Random().nextInt(999999999),
                UUID.randomUUID().toString(),
                userCreateDto.userName(), userCreateDto.email(), userCreateDto.password(), Date.valueOf(LocalDate.now()));
    }
}

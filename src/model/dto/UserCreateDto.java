package model.dto;

import java.sql.Date;

public record UserCreateDto(
        String userName,
        String email,
        String password
) {
}

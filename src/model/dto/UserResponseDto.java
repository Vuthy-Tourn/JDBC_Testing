package model.dto;

import java.sql.Date;

public record UserResponseDto(
        String uuid,
        String userName,
        String email,
        Date createdDate
) {
}

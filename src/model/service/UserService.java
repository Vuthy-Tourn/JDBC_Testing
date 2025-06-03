package model.service;

import model.dto.*;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    UserResponseDto insertNewUser(UserCreateDto user);
    Integer deleteUserByUuid(String uuid);
    UserResponseDto getUserByUuid(String uuid);
    UserResponseDto updateUserByUuid(String uuid, UpdateUserDto updateUserDto);
}

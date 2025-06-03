package controller;

import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UpdateUserDto;
import model.service.UserServiceImpl;

import java.util.List;

public class UserController {
    private UserServiceImpl userService = new UserServiceImpl();
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }
    public UserResponseDto insertNewUser(UserCreateDto userCreateDto){
        return userService
                .insertNewUser(userCreateDto);
    }
    public UserResponseDto getUserByUuid(String uuid){
        return userService.getUserByUuid(uuid);
    }
    public Integer deleteUserByUuid(String uuid){
        return userService.deleteUserByUuid(uuid);
    }
    public UserResponseDto updateUserByUuid(String uuid, UpdateUserDto updateUserDto){
        return userService.updateUserByUuid(uuid, updateUserDto);
    }
}

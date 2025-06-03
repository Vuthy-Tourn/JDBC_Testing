package model.service;

import mapper.ProductMapper;
import mapper.UserMapper;
import model.dto.UserResponseDto;
import model.dto.UpdateUserDto;
import model.dto.UserCreateDto;

import model.entities.User;
import model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepository();
    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserResponseDto> userResponseDtoList
                = new ArrayList<>();
        userRepository.findAll()
                .stream()
                .forEach(p->{
                    userResponseDtoList
                            .add(new UserResponseDto(p.getUuid()
                                    , p.getUserName(),
                                    p.getEmail(),
                                    p.getCreatedDate()));
                });
        return userResponseDtoList;
    }

    @Override
    public UserResponseDto insertNewUser(UserCreateDto userCreateDto) {
        User user1
                = UserMapper.mapFromUserCreateDtoToUser(userCreateDto);
        return UserMapper.mapFromUserToUserResponseDto(
                userRepository.save(user1)
        );
    }

    @Override
    public Integer deleteUserByUuid(String uuid) {
        try{
            User user = userRepository.findByUserUuid(uuid);
            return userRepository.delete(user.getId());
        }catch (NoSuchElementException exception){
            System.out.println("Error during deleting User by Uuid:" + exception.getMessage());
        }
        return 0;
    }

    @Override
    public UserResponseDto getUserByUuid(String uuid) {
        try{
            return UserMapper.mapFromUserToUserResponseDto(
                    userRepository.findByUserUuid(uuid)
            );
        }catch (NoSuchElementException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public UserResponseDto updateUserByUuid(String uuid, UpdateUserDto updateUserDto) {
        try {
            return UserMapper
                    .mapFromUserToUserResponseDto(
                            userRepository.updateUserByUuid(uuid, updateUserDto)
                    );
        }catch (NoSuchElementException exception){
            System.out.println(exception.getMessage()
            );
        }
        return null;
    }
}

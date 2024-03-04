package com.blog.services;

import com.blog.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    //Create a new User
    public UserDto createUser(UserDto user);
    //Update a Particular User by usuerId
    public UserDto updateUser(UserDto userDto, Integer userId);
    //Get Use by userId
    public UserDto getUserById(Integer userId);
    //Get all Users
    public List<UserDto> getAllUser();
    //Delete user by userId
    public String deleteUserById(Integer userId);
    //Register User
    public UserDto registerNewUser(UserDto userDto);
}

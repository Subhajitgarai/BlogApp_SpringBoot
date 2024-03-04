package com.blog.controller;

import com.blog.payloads.UserDto;
import com.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    //Create a User
    @PostMapping("/")
    public ResponseEntity<UserDto> CreateNewUser(@Valid @RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    //GetAll UserDetails
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUserDetails(){
        List<UserDto> allUser = userService.getAllUser();
        return new ResponseEntity<>(allUser,HttpStatus.OK);

    }
    //Get Single User
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUserById(@PathVariable Integer userId){
        UserDto userById = userService.getUserById(userId);
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }
    //Update UserDetail
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUserDetails(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
        UserDto updatedUserDetail = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDetail,HttpStatus.OK);
    }
    //Delete user by userId
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable Integer userId){
        String msg = userService.deleteUserById(userId);
        return new ResponseEntity<>(msg,HttpStatus.OK);
    }


}

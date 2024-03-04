package com.blog.services;

import com.blog.config.AppConstants;
import com.blog.dao.RoleRepo;
import com.blog.dao.UserRepo;
import com.blog.enteties.Role;
import com.blog.enteties.UserDetail;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    //Create a User
    public UserDto createUser(UserDto user) {
        UserDetail savedUser = userRepo.save(doToUserDetail(user));
        return doToUserDto(savedUser);
    }

    //Update a user
    public UserDto updateUser(UserDto userDto, Integer userId) {
        UserDetail userDetail = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserDetail", "id", userId));
        userDetail.setName(userDto.getName());
        userDetail.setAbout(userDto.getAbout());
        userDetail.setPassword(userDto.getPassword());
        userDetail.setEmail(userDto.getEmail());
        UserDetail savedUser = userRepo.save(userDetail);
        return doToUserDto(savedUser);
    }

    //Get User By Id
    public UserDto getUserById(Integer userId) {
        UserDetail userDetail = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserDetail", "id", userId));
        return doToUserDto(userDetail);
    }

    //Get All UserDetail
    public List<UserDto> getAllUser() {
        List<UserDetail> allUserDetail = userRepo.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        allUserDetail.forEach(userDetail -> {
            userDtos.add(doToUserDto(userDetail));
        });
        return userDtos;

    }

    //Delete user by userId
    public String deleteUserById(Integer userId) {
        UserDetail userDetail = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserDetail", "id", userId));
        userRepo.deleteById(userId);
        return "User Deleted !!";
    }
    //Register User
    @Override
    public UserDto registerNewUser(UserDto userDto) {
        UserDetail mapUser = modelMapper.map(userDto, UserDetail.class);
        //Encode the Password
        mapUser.setPassword(passwordEncoder.encode(mapUser.getPassword()));
        //Roles
        Optional<Role> role = roleRepo.findById(AppConstants.NORMAL_USER);
        Role role1 = role.get();
        mapUser.setRoles(List.of(role1));
        UserDetail savedUser = userRepo.save(mapUser);
        return modelMapper.map(savedUser, UserDto.class);
    }


    //Converting UserDetails to UserDto
    public UserDetail doToUserDetail(UserDto userDto) {
//        UserDetail user = new UserDetail();
//        user.setUserId(userDto.getUserId());
//        user.setAbout(userDto.getAbout());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        return user;
//        By ModelMapper
        return modelMapper.map(userDto,UserDetail.class);
    }

    //Converting UserDto to UserDetails
    public UserDto doToUserDto(UserDetail userDetail) {
//        UserDto userDto = new UserDto();
//        userDto.setUserId(userDetail.getUserId());
//        userDto.setEmail(userDetail.getEmail());
//        userDto.setPassword(userDetail.getPassword());
//        userDto.setAbout(userDetail.getAbout());
//        userDto.setName(userDetail.getName());
//        return userDto;
        return modelMapper.map(userDetail, UserDto.class);

    }
}

package com.blog.controller;

import com.blog.payloads.UserDto;
import com.blog.security.JWTRequest;
import com.blog.security.JWTResponse;
import com.blog.security.JwtHelper;
import com.blog.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    //Methode for login
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {
        //Checking User is Valid Or not
        this.doAuthenticate(request.getEmail(), request.getPassword());
        //If Valid then getting the user details for token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JWTResponse response = JWTResponse.builder()
                .jwtToken(token)
                .useername(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //Methode to validate the email and password
    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            daoAuthenticationProvider.authenticate(authentication);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }
    //If Any Exception Occurs it will come here and print this
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    //Register New User Api
    @PostMapping("/register")
    public ResponseEntity<UserDto>registerUser(@RequestBody UserDto userDto){
        UserDto registered = userService.registerNewUser(userDto);
        return new ResponseEntity<>(registered,HttpStatus.CREATED);
    }


}

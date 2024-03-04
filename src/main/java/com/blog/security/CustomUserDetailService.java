package com.blog.security;

import com.blog.dao.UserRepo;
import com.blog.enteties.UserDetail;
import com.blog.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
//For Dao Authentications
@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
        return userDetail;
    }
}

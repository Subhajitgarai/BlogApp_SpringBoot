package com.blog.dao;

import com.blog.enteties.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserDetail,Integer> {
    Optional<UserDetail>findByEmail(String email);
}

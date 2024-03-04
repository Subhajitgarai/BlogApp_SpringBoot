package com.blog.dao;

import com.blog.enteties.Category;
import com.blog.enteties.Post;
import com.blog.enteties.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post>findByUserDetail(UserDetail userDetail);
    List<Post>findByCategory(Category category);
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> getPostByKeywords(String keyword);
}

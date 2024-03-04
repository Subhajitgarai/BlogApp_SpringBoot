package com.blog.services;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    //Create a new Post
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    //Update a Post
    PostDto updatePost(PostDto postDto, Integer postId);
    //Delete a Post
    String deletePost(Integer postId);
    //Get All Post
    public PostResponse getAllPost(int pageNumber, int pageSize,String sortBy,String sortDir);
    //Get Post By Id
    PostDto getPostById(Integer postId);
    //Get All Post By Category
    List<PostDto> getPostByCategory(Integer categoryId);
    //Get All PostBy User
    List<PostDto> getAllPostByUser(Integer userId);
    //Search posts
    List<PostDto> searchPost(String keyword);
}

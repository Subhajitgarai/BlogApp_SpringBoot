package com.blog.services;

import com.blog.dao.CategoryRepo;
import com.blog.dao.PostRepo;
import com.blog.dao.UserRepo;
import com.blog.enteties.Category;
import com.blog.enteties.Post;
import com.blog.enteties.UserDetail;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        UserDetail userDetailById = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category categoryById = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setAddDate(new Date());
        post.setImageName("default.png");
        post.setCategory(categoryById);
        post.setUserDetail(userDetailById);
        Post saved = postRepo.save(post);
        return modelMapper.map(saved, PostDto.class);

    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        postRepo.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public String deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        postRepo.deleteById(postId);
        return "Post Deleted With id = " + postId;
    }

    @Override
    public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Getting Data in pages
        //Sorting Direction
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepo.findAll(pageable);
        List<Post> allPost = pagePost.getContent();
        List<PostDto> postConverted = new ArrayList<>();
        allPost.forEach(post -> {
            PostDto map = modelMapper.map(post, PostDto.class);
            postConverted.add(map);
        });
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postConverted);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }


    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category categoryById = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Post> byCategoryPost = postRepo.findByCategory(categoryById);
        List<PostDto> postdtos = new ArrayList<>();
        byCategoryPost.forEach(post -> {
            PostDto map = modelMapper.map(post, PostDto.class);
            postdtos.add(map);
        });
        return postdtos;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        UserDetail userDetailById = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Post> byUserDetailPost = postRepo.findByUserDetail(userDetailById);
        List<PostDto> postdtos = new ArrayList<>();
        byUserDetailPost.forEach(post -> {
            PostDto map = modelMapper.map(post, PostDto.class);
            postdtos.add(map);
        });
        return postdtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> postByKeywords = postRepo.getPostByKeywords(keyword);
        List<PostDto> postDtos = new ArrayList<>();
        postByKeywords.forEach(post -> {
            PostDto map = modelMapper.map(post, PostDto.class);
            postDtos.add(map);
        });
        return postDtos;
    }
}

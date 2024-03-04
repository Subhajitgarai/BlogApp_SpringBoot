package com.blog.controller;

import com.blog.config.AppConstants;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@EnableMethodSecurity(prePostEnabled = true)
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    //Create a post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto createdPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //Get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPostDetails(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY ,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR ,required = false)String sortDir) {

        System.out.println("Received pageNumber: " + pageNumber);
        System.out.println("Received pageSize: " + pageSize);
        PostResponse allPost = postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(allPost, HttpStatus.OK);
    }


    //Get a post by postId
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postById = postService.getPostById(postId);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    //Delete a post by postId
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Integer postId) {
        String msg = postService.deletePost(postId);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    //Get Post By User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDto> allPostByUser = postService.getAllPostByUser(userId);
        return new ResponseEntity<>(allPostByUser, HttpStatus.OK);
    }

    //Get All Posts By Category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> postByCategory = postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postByCategory, HttpStatus.OK);
    }

    //Update a Post Details
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    //Search a post by keywords for titles
    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> getPostsByKeywords(@Param("keywords") String keywords) {
        List<PostDto> postDtos = postService.searchPost(keywords);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
    //Post Image Upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto>uploadPostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws IOException {
        PostDto postById = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postById.setImageName(fileName);
        PostDto updatedPost = postService.updatePost(postById, postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
    //Get An Image By Image Id
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(@PathVariable String imageName, HttpServletResponse response) throws Exception {
        InputStream resource=fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}

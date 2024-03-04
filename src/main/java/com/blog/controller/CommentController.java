package com.blog.controller;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;
    //Get All Comments of a particular postId
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto>createComment(@RequestBody CommentDto comment, @PathVariable Integer postId){
        CommentDto createdComment = commentService.createComment(comment, postId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
    //Delete Comments By CommentId
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteCommentById(@PathVariable Integer commentId){
        String msg = commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(msg,true),HttpStatus.OK);
    }
}

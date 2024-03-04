package com.blog.services;

import com.blog.payloads.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    //Create a Comment of a particular post
    CommentDto createComment(CommentDto commentDto,Integer postId);
    //Delete a comment by commentId
    String deleteComment(Integer commentId);
}

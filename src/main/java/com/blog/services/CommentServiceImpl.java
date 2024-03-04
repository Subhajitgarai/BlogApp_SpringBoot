package com.blog.services;

import com.blog.dao.CommentRepo;
import com.blog.dao.PostRepo;
import com.blog.enteties.Comment;
import com.blog.enteties.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentServiceImpl implements CommentService{
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);

    }

    @Override
    public String deleteComment(Integer commentId) {
        Comment commentById = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        commentRepo.deleteById(commentId);
        return "Comment Deleted !!";

    }
}

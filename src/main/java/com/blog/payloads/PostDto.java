package com.blog.payloads;

import com.blog.enteties.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String postId;
    private String title;
    private String content;
    private String imageName;
    private Date addDate;
    private CategoryDto category;
    private UserDto userDetail;
    private List<CommentDto>comments;
}

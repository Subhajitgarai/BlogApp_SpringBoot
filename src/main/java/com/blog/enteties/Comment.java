package com.blog.enteties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id",referencedColumnName = "postId")
    private Post post;


}

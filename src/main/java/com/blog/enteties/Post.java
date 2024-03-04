package com.blog.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post_details")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false,length = 10000)
    private String content;
    private String imageName;
    @Column(nullable = false)
    private Date addDate;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetail userDetail;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment>comments;
    //Only Mapped By Table has the Foreign Key like here the comment table will have Fk


}

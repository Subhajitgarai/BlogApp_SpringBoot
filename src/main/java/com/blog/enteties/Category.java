package com.blog.enteties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    @Column(nullable = false)
    private String categoryTitle;
    @Column(nullable = false)
    private String categoryDescription;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Post>posts;
}

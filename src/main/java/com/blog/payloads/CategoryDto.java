package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private int categoryId;
    @NotBlank
    private String categoryTitle;
    @NotBlank
    private String categoryDescription;
}

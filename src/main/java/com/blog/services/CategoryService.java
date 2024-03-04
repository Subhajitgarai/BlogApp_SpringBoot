package com.blog.services;

import com.blog.payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    //Add a Category
    public CategoryDto addCategory(CategoryDto categoryDto);
    //Update a Category
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    //Get All Categories details
    public List<CategoryDto> getAllCategory();
    //Get Category By categoryId
    public CategoryDto getSingleCategory(Integer categoryId);
    //Delete a Category by categoryId
    public String deleteCategory(Integer categoryId);
}

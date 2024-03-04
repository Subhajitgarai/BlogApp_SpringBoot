package com.blog.services;

import com.blog.dao.CategoryRepo;
import com.blog.enteties.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    //Add a Category
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category savedCategory = categoryRepo.save(modelMapper.map(categoryDto, Category.class));
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    //Update a Category
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category categoryById = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        categoryById.setCategoryDescription(categoryDto.getCategoryDescription());
        categoryById.setCategoryTitle(categoryDto.getCategoryTitle());
        Category savedCategory = categoryRepo.save(modelMapper.map(categoryById, Category.class));
        return modelMapper.map(savedCategory, CategoryDto.class);

    }

    //Get All Category
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategory = categoryRepo.findAll();
        List<CategoryDto> cateDto = new ArrayList<>();
        allCategory.forEach(category -> {
            CategoryDto convertedCategory = modelMapper.map(category, CategoryDto.class);
            cateDto.add(convertedCategory);
        });
        return cateDto;

    }

    //Get Single Category
    public CategoryDto getSingleCategory(Integer categoryId) {
        Category categoryById = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        return modelMapper.map(categoryById, CategoryDto.class);
    }

    //Delete a Category
    public String deleteCategory(Integer categoryId) {
        Category categoryById = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        categoryRepo.deleteById(categoryId);
        return "Category Deleted !!";

    }
}

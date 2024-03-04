package com.blog.controller;

import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //Add a Category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createNewCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(savedCategory, HttpStatus.CREATED);

    }

    //Update a Category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    //Get All Categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        return new ResponseEntity<>(allCategory, HttpStatus.OK);

    }

    //Get Single Category by CategoryId
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);
        return new ResponseEntity<>(singleCategory, HttpStatus.OK);
    }

    //Delete a Category By CategoryId
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Integer categoryId) {
        String s = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}

package com.love.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.love.exceptions.ResourceAlreadyExistsException;
import com.love.exceptions.ResourceNotFoundException;
import com.love.model.Category;
import com.love.response.ApiResponse;
import com.love.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService iCategoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> allCategories = iCategoryService.getAllCategories();
            return ResponseEntity.ok((new ApiResponse("Found!", allCategories)));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category) {
        try {
            Category savedCategory = iCategoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Success!", savedCategory));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-category-by-id/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = iCategoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Success!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get-category-by-name/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {
        try {
            Category category = iCategoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new ApiResponse("Success!", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-category-by-id/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long categoryId) {
        try {
            iCategoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Delete Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/update-category-by-id/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId , @RequestBody Category category) {
        try {
            Category updateCategory = iCategoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new ApiResponse("Update Success!", updateCategory));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
